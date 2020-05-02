package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
  public void testGetQuoteOk() throws DalException, BizException {
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


  /*
   * @Test
   * 
   * @DisplayName("test getQuotes") public void testGetQuotes() throws BizException {
   * assertNotNull(qcc.getQuotes()); }
   */

  @Test
  @DisplayName("test getQuotesFiltered with null parameter")
  public void testGetQuotesFiltered1() throws DalException {
    assertNotNull(qcc.getQuotesFiltered(null));
  }

  @Test
  @DisplayName("test getQuotesFiltered with empty filter")
  public void testGetQuotesFiltered2() throws DalException {
    assertTrue(!(qcc.getQuotesFiltered(quotesFilter).isEmpty()));
  }

  @Test
  @DisplayName("test getQuotesFiltered with a filter")
  public void testGetQuotesFiltered3() throws DalException {
    quotesFilter.setCustomerName("James");
    assertTrue(qcc.getQuotesFiltered(quotesFilter).size() > 0);
  }

  @Test
  @DisplayName("test useStateManager when all is good")
  public void testUseStateManagerOk() throws BizException, DalException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("introduit");
    quote.setState(QuoteState.QUOTE_ENTERED);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest), () -> assertNotNull(quoteToTest.getStartDate()),
        () -> assertTrue(QuoteState.PLACED_ORDERED == quoteToTest.getState()));
  }

  @Test
  @DisplayName("test useStateManager when all is good  and state == CONFIRMED DATE"
      + " but workduration > 15")
  public void testUseStateManagerOk2() throws BizException, DalException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("dateConfirme");
    quote.setState(QuoteState.CONFIRMED_DATE);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertTrue(QuoteState.PARTIAL_INVOICE == quoteToTest.getState()));

  }

  @Test
  @DisplayName("test useStateManager when all is good  and state == CONFIRMED DATE"
      + "  but workduration <= 15")
  public void testUseStateManagerOk3() throws BizException, DalException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("Total");
    quote.setState(QuoteState.CONFIRMED_DATE);
    quote.setIdCustomer(1);

    QuoteDto quoteToTest = qcc.useStateManager(quote);

    assertAll(() -> assertNotNull(quoteToTest),
        () -> assertTrue(QuoteState.TOTAL_INVOICE == quoteToTest.getState()));

  }


  @Test
  @DisplayName("test useStateManager when idQuote == null")
  public void testUseStateManagerko1() throws BizException, DalException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote(null);
    quote.setState(QuoteState.QUOTE_ENTERED);
    quote.setIdCustomer(1);

    assertThrows(BizException.class, () -> qcc.useStateManager(quote));
  }


}
