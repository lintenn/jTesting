package org.luis.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;


class AdvertisementBoardTest {
  private class DummyAdvertiserDatabase implements AdvertiserDatabase {
    @Override
    public boolean findAdviser(String adviserName) {
      // This method will not be used, so we do not mind what it returns.
      return true;
    }
  }

  private class DummyPaymentDatabase implements PaymentDatabase {
    @Override
    public void advertisementPublished(String advertiserName) {
      // This method will not be used.
    }

    @Override
    public boolean advertiserHasFunds(String advertiserName) {
      // This method will not be used, so we do not mind what it returns.
      return true;
    }
  }

  /**
   * Comprobar que inicialmente hay un anuncio en el tablón.
   */
  @Test
  public void ABoardHasAnAdvertisementWhenItIsCreated() {
    // Arrange - Act
    AdvertisementBoard board = new AdvertisementBoard();

    // Assert
    assertThat(board.numberOfPublishedAdvertisements()).isEqualTo(1);
  }

  /**
   * Crear un anuncio por parte de "THE Company", insertarlo y
   * comprobar que se ha incrementado en uno el número de anuncios del tablón.
   */
  @Test
  public void PublishAnAdvertisementByTheCompanyIncreasesTheNumberOfAdvertisementsByOne() {
    // Arrange
    AdvertisementBoard board = new AdvertisementBoard();
    Advertisement advertisement = new Advertisement("Advertisement", "Description", "THE Company");
    // We can use dummies for the AdvertiserDatabase and PaymentDatabase, because their methods will not be invoked.
    AdvertiserDatabase dummyAdvertiserDatabase = new DummyAdvertiserDatabase();
    PaymentDatabase dummyPaymentDatabase = new DummyPaymentDatabase();

    // Act
    int expectedValue = board.numberOfPublishedAdvertisements() + 1; // 1 + 1
    board.publish(advertisement, dummyAdvertiserDatabase, dummyPaymentDatabase);
    int obtainedValue = board.numberOfPublishedAdvertisements(); // 2

    // Assert
    assertThat(obtainedValue).isEqualTo(expectedValue);
  }

  /**
   * Publicar un anuncio por parte del anunciante "Pepe Gotera y Otilio" y comprobar que,
   * si está en la base de datos de anunciantes pero no tiene saldo, el anuncio no se inserta,
   * lo que se determina observando que el número de anuncios no aumenta.
   */
  @Test
  public void WhenAnAdvertiserHasNoFoundsTheAdvertisementIsNotPublished() {
    // Arrange
    AdvertisementBoard board = new AdvertisementBoard();
    Advertisement advertisement = new Advertisement("Advertisement", "Description", "Pepe Gotera y Otilio");

    // Stubbing
    AdvertiserDatabase stubAdvertiserDatabase = mock(AdvertiserDatabase.class);
    when(stubAdvertiserDatabase.findAdviser("Pepe Gotera y Otilio")).thenReturn(true);
    PaymentDatabase stubPaymentDatabase = mock(PaymentDatabase.class);
    when(stubPaymentDatabase.advertiserHasFunds("Pepe Gotera y Otilio")).thenReturn(false);

    // Act
    int expectedValue = board.numberOfPublishedAdvertisements(); // 1
    board.publish(advertisement, stubAdvertiserDatabase, stubPaymentDatabase);
    int obtainedValue = board.numberOfPublishedAdvertisements(); // 1

    // Assert
    assertThat(obtainedValue).isEqualTo(expectedValue);
  }

  /**
   * Publicar un anuncio por parte de un anunciante llamado "Robin Robot",
   * asumiendo que está en la base de datos de anunciantes, que tiene saldo y
   * finalmente comprobando que se ha invocado el método "advertisementPublished()".
   */
  @Test
  public void AnAdvertisementIsPublishedIfTheAdvertiserIsRegisteredAndHasFunds() {
    // Arrange
    AdvertisementBoard board = new AdvertisementBoard();
    Advertisement advertisement = new Advertisement("Advertisement", "Description", "Robin Robot");

    // Stubbing
    AdvertiserDatabase mockAdvertiserDatabase = mock(AdvertiserDatabase.class);
    when(mockAdvertiserDatabase.findAdviser("Robin Robot")).thenReturn(true);
    PaymentDatabase mockPaymentDatabase = mock(PaymentDatabase.class);
    when(mockPaymentDatabase.advertiserHasFunds("Robin Robot")).thenReturn(true);

    // Act
    int expectedValue = board.numberOfPublishedAdvertisements() + 1; // 1 + 1
    board.publish(advertisement, mockAdvertiserDatabase, mockPaymentDatabase);
    int obtainedValue = board.numberOfPublishedAdvertisements(); // 2

    // Assert
    assertThat(obtainedValue).isEqualTo(expectedValue);
    verify(mockPaymentDatabase, times(1)).advertisementPublished("Robin Robot");
  }

  /**
   * Publicar dos anuncios por parte de "THE Company", borrar el primero y
   * comprobar que si se busca ya no está en el tablón.
   */
  @Test
  public void WhenTheOwnerMakesTwoPublicationsAndTheFirstOneIsDeletedItIsNotInTheBoard() {
    // Arrange
    AdvertisementBoard board = new AdvertisementBoard();
    Advertisement advertisement = new Advertisement("Advertisement 1", "Description", "THE Company");
    Advertisement advertisement2 = new Advertisement("Advertisement 2", "Description", "THE Company");
    // We can use dummies for the AdvertiserDatabase and PaymentDatabase, because their methods will not be invoked.
    AdvertiserDatabase dummyAdvertiserDatabase = new DummyAdvertiserDatabase();
    PaymentDatabase dummyPaymentDatabase = new DummyPaymentDatabase();

    // Act
    board.publish(advertisement, dummyAdvertiserDatabase, dummyPaymentDatabase);
    board.publish(advertisement2, dummyAdvertiserDatabase, dummyPaymentDatabase);
    board.deleteAdvertisement("Advertisement 1", "THE Company");

    // Assert
    assertThat(board.findByTitle("Advertisement 1")).isNull();
  }

  /**
   * Comprobar que si se intenta insertar un anuncio que ya existe (mismo título y mismo anunciante),
   * no se realiza la inserción y el método "advertisementPublished()" no se ha invocado.
   * Para pasar esta prueba hay que modificar la clase "AdvertisementBoard".
   */
  @Test
  public void AnExistingAdvertisementIsNotPublished() {
    // Arrange
    AdvertisementBoard board = new AdvertisementBoard();
    Advertisement advertisement = new Advertisement("Advertisement", "Description", "Pepe Gotera y Otilio");
    Advertisement advertisement2 = new Advertisement("Advertisement", "Description", "Pepe Gotera y Otilio");

    // Stubbing
    AdvertiserDatabase mockAdvertiserDatabase = mock(AdvertiserDatabase.class);
    when(mockAdvertiserDatabase.findAdviser("Pepe Gotera y Otilio")).thenReturn(true);
    PaymentDatabase mockPaymentDatabase = mock(PaymentDatabase.class);
    when(mockPaymentDatabase.advertiserHasFunds("Pepe Gotera y Otilio")).thenReturn(true);

    // Act
    board.publish(advertisement, mockAdvertiserDatabase, mockPaymentDatabase);
    board.publish(advertisement2, mockAdvertiserDatabase, mockPaymentDatabase);

    // Assert
    int expectedValue = 2;
    int obtainedValue = board.numberOfPublishedAdvertisements(); // 1 + 1 + 0

    assertThat(obtainedValue).isEqualTo(expectedValue);
    // "advertisementPublished()" was only invoked once and not twice (an existing advertisement was not published)
    verify(mockPaymentDatabase, times(1)).advertisementPublished(advertisement2.advertiser);

  }

  /**
   * Comprobar que si se intenta insertar un anuncio por parte del anunciante "Tim O'Theo" y
   * el tablón está lleno se eleva la excepción "FullBoardException".
   * Para pasar esta prueba hay que modificar la clase "AdvertisementBoard".
   */
  @Test
  public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {
    // Arrange
    AdvertisementBoard board = new AdvertisementBoard();
    AdvertisementBoard spyBoard = spy(board);
    Advertisement advertisement = new Advertisement("Advertisement T", "Description", "Tim O'Theo");
    // We can use dummies for the AdvertiserDatabase and PaymentDatabase, because their methods will not be invoked.
    AdvertiserDatabase dummyAdvertiserDatabase = new DummyAdvertiserDatabase();
    PaymentDatabase dummyPaymentDatabase = new DummyPaymentDatabase();

    // We can use the spy to simulate the behaviour of a full board.
    doReturn(20).when(spyBoard).numberOfPublishedAdvertisements();

    // Act - Assert
    assertThatThrownBy(() -> spyBoard.publish(advertisement, dummyAdvertiserDatabase, dummyPaymentDatabase))
        .isInstanceOf(FullBoardException.class);
  }

}