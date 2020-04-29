package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
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
  }

  @DisplayName("qcc test different from null")
  @Test
  public void testQcc() {
    assertNotNull(qcc);
  }

  @DisplayName("test get quote when you give him a good idQuote")
  @Test
  public void testGetQuoteOk() throws FatalException, BizException {
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
  @DisplayName("test getQuotes")
  public void testGetQuotes() throws BizException {
    assertNotNull(qcc.getQuotes());
  }

  @Test
  @DisplayName("test getQuotesFiltered with null parameter")
  public void testGetQuotesFiltered() throws FatalException {
    assertNotNull(qcc.getQuotesFiltered(null));
  }

  @Test
  @DisplayName("test useStateManager when all is good")
  public void testUseStateManagerOk() throws BizException, FatalException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote("introduit");
    quote.setState(QuoteState.QUOTE_ENTERED);
    quote.setIdCustomer(1);

    assertNotNull(qcc.useStateManager(quote));
  }

  @Test
  @DisplayName("test useStateManager when idQuote == null")
  public void testUseStateManagerko1() throws BizException, FatalException {
    QuoteDto quote = dtoFactory.getQuote();

    quote.setIdQuote(null);
    quote.setState(QuoteState.QUOTE_ENTERED);
    quote.setIdCustomer(1);

    assertThrows(BizException.class, () -> qcc.useStateManager(quote));
  }

  @Test
  @DisplayName("test setState when all is good")
  public void testSetStateOk() throws BizException, FatalException {

    assertNotNull(qcc.setState("ok", QuoteState.QUOTE_ENTERED));
  }

  @Test
  @DisplayName("test setState when idQuote is null")
  public void testSetStateKo1() throws BizException, FatalException {

    assertThrows(BizException.class, () -> qcc.setState(null, QuoteState.CONFIRMED_DATE));
  }



}