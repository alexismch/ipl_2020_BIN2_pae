package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

public class QuoteUccImplTest {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private QuoteUcc qcc;

  private QuotesFilterDto quotesFilter;

  /**
   * Allows you to make the necessary injections.
   */
  @BeforeEach
  public void setUp() {
    PropertiesLoader propertiesLoader = new PropertiesLoader();
    try {
      propertiesLoader.loadProperties("props/test.properties");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    InjectionService injectionService = new InjectionService(propertiesLoader);
    injectionService.inject(this);

    quotesFilter = dtoFactory.getQuotesFilter();
  }

  @DisplayName("qcc test different from null")
  @Test
  public void testQcc() {
    assertNotNull(qcc);
  }

  @DisplayName("test get quote when you give him a good idQuote")
  @Test
  public void testGetQuoteOk() throws BizException {
    QuoteDto quote = qcc.getQuote("ok");
    assertAll(() -> assertNotNull(quote), () -> assertEquals(1, quote.getIdCustomer()),
        () -> assertFalse(quote.getListPhotoBefore().isEmpty()),
        () -> assertFalse(quote.getListPhotoAfter().isEmpty()),
        () -> assertTrue(quote.getListPhotoBefore().get(0).isBeforeWork()),
        () -> assertFalse(quote.getListPhotoAfter().get(0).isBeforeWork()));
  }

  @DisplayName("id quote is null")
  @Test
  public void testGetQuoteKo1() {
    assertThrows(BizException.class, () -> qcc.getQuote(null));
  }

  @Test
  @DisplayName("test getQuotesFiltered with null parameter")
  public void testGetQuotesFiltered1() {
    assertNotNull(qcc.getQuotesFiltered(null));
  }

  @Test
  @DisplayName("test getQuotesFiltered with empty filter")
  public void testGetQuotesFiltered2() {
    assertFalse(qcc.getQuotesFiltered(quotesFilter).isEmpty());
  }

  @Test
  @DisplayName("test getQuotesFiltered with a filter")
  public void testGetQuotesFiltered3() {
    quotesFilter.setCustomerName("James");
    assertTrue(qcc.getQuotesFiltered(quotesFilter).size() > 0);
  }

  @Test
  @DisplayName("test useStateManager when all is good")
  public void testUseStateManagerOk() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("introduit");
    quote.setState(QuoteState.QUOTE_ENTERED);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest), () -> assertNotNull(quoteToTest.getStartDate()),
        () -> assertSame(QuoteState.PLACED_ORDERED, quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good  and state == CONFIRMED DATE"
      + " but workduration > 15")
  public void testUseStateManagerOk2() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("dateConfirme2");
    quote.setState(QuoteState.CONFIRMED_DATE);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertSame(QuoteState.PARTIAL_INVOICE, quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good  and state == CONFIRMED DATE"
      + "  but workduration <= 15")
  public void testUseStateManagerOk3() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("dateConfirme");
    quote.setState(QuoteState.CONFIRMED_DATE);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertSame(QuoteState.TOTAL_INVOICE, quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good  and state == PARTIAL_INVOICE")
  public void testUseStateManagerOk4() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("partiel");
    quote.setState(QuoteState.PARTIAL_INVOICE);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertSame(QuoteState.TOTAL_INVOICE, quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good  and state == Total_INVOICE")
  public void testUseStateManagerOk5() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("Total");
    quote.setState(QuoteState.TOTAL_INVOICE);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertSame(QuoteState.VISIBLE, quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good  and state == Cancelled")
  public void testUseStateManagerOk6() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("annule");
    quote.setState(QuoteState.CANCELLED);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertSame(QuoteState.CANCELLED, quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good and state == VISIBLE")
  public void testUseStateManagerOk7() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("ok");
    quote.setState(QuoteState.VISIBLE);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertNull(quoteToTest);
  }

  @Test
  @DisplayName("test useStateManager when all is good and state == PLACED_ORDERED")
  public void testUseStateManagerOk8() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("commandePassee");
    quote.setState(QuoteState.PLACED_ORDERED);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertSame(QuoteState.CONFIRMED_DATE, quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good and state == PLACED_ORDERED")
  public void testUseStateManagerOk9() throws BizException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("commandePassee");
    quote.setState(QuoteState.PLACED_ORDERED);
    quote.setStartDate(LocalDate.of(2020, 1, 20));

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertSame(QuoteState.PLACED_ORDERED, quoteToTest.getState()),
        () -> assertEquals(2020, quote.getStartDate().getYear()),
        () -> assertEquals(1, quote.getStartDate().getMonthValue()),
        () -> assertEquals(20, quote.getStartDate().getDayOfMonth()));
  }

  @Test
  @DisplayName("test useStateManager when idQuote == null")
  public void testUseStateManagerKo1() {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote(null);
    quote.setState(QuoteState.QUOTE_ENTERED);
    quote.setIdCustomer(1);

    assertThrows(BizException.class, () -> qcc.useStateManager(quote));
  }

  @Test
  @DisplayName("test useStateManager when quote state != quote.getState")
  public void testUseStateManagerKo2() {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("partiel");
    quote.setState(QuoteState.TOTAL_INVOICE);

    assertThrows(BizException.class, () -> qcc.useStateManager(quote));
  }

  @Test
  @DisplayName("test insertQuote with existent quoteId")
  public void testInsertQuoteKo() {
    QuoteDto quoteDto = dtoFactory.getQuote();
    quoteDto.setIdQuote("false");
    assertThrows(BizException.class, () -> qcc.insert(quoteDto));
  }

  @Test
  @DisplayName("test insertQuote with non-existent quoteId")
  public void testInsertQuoteOk() {
    QuoteDto quoteDto = dtoFactory.getQuote();

    DevelopmentTypeDto developmentTypeDto = dtoFactory.getDevelopmentType();
    developmentTypeDto.setIdType(1);
    quoteDto.addDevelopmentType(developmentTypeDto);

    quoteDto.addToListPhotoBefore(dtoFactory.getPhoto());

    quoteDto.setIdQuote("true");
    assertDoesNotThrow(() -> qcc.insert(quoteDto));
  }

  @DisplayName("test set favorite photo with non-existent quote")
  @Test
  public void testSetFavoritePhotoKo1() {
    assertThrows(BizException.class, () -> qcc.setFavoritePhoto(null, 2));
  }

  @DisplayName("test set favorite photo with non-existent photo")
  @Test
  public void testSetFavoritePhotoKo2() {
    assertThrows(BizException.class, () -> qcc.setFavoritePhoto("ok", 8));
  }

  @DisplayName("test set favorite photo with non-linked photo")
  @Test
  public void testSetFavoritePhotoKo3() {
    assertThrows(BizException.class, () -> qcc.setFavoritePhoto("ok", 5));
  }

  @DisplayName("test set favorite photo with linked photo")
  @Test
  public void testSetFavoritePhotoOk() {
    assertDoesNotThrow(() -> qcc.setFavoritePhoto("ok", 4));
  }

  @DisplayName("test set start date with wrong state")
  @Test
  public void testSetStartDateQuoteKo() {
    QuoteDto quoteDto = dtoFactory.getQuote();
    assertAll(
        () -> {
          quoteDto.setIdQuote("dateConfirme");
          assertThrows(BizException.class, () -> qcc.setStartDateQuoteInDb(quoteDto));
        },
        () -> {
          quoteDto.setIdQuote("partiel");
          assertThrows(BizException.class, () -> qcc.setStartDateQuoteInDb(quoteDto));
        },
        () -> {
          quoteDto.setIdQuote("Total");
          assertThrows(BizException.class, () -> qcc.setStartDateQuoteInDb(quoteDto));
        },
        () -> {
          quoteDto.setIdQuote("ok");
          assertThrows(BizException.class, () -> qcc.setStartDateQuoteInDb(quoteDto));
        }
    );
  }

  @DisplayName("test set start date with good state")
  @Test
  public void testSetStartDateQuoteOk() {
    QuoteDto quoteDto = dtoFactory.getQuote();
    quoteDto.setStartDate(LocalDate.now());
    assertAll(
        () -> {
          quoteDto.setIdQuote("introduit");
          assertDoesNotThrow(() -> qcc.setStartDateQuoteInDb(quoteDto));
        },
        () -> {
          quoteDto.setIdQuote("commandePassee");
          assertDoesNotThrow(() -> qcc.setStartDateQuoteInDb(quoteDto));
        }
    );
  }
}
