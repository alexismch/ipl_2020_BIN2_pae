package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
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

  /*
   * //TODO SOUFIANE
   * 
   * @DisplayName("test confirm quote ok")
   * 
   * @Test public void testConfirmQuoteOk() throws FatalException, BizException { QuoteDto quote =
   * qcc.confirmQuote("confirm"); assertEquals(QuoteState.PLACED_ORDERED, quote.getState()); }
   * 
   * @DisplayName("test confirm quote not ok")
   * 
   * @Test public void testConfirmQuoteKo() throws FatalException, BizException { QuoteDto quote =
   * qcc.confirmQuote("ko"); assertNotEquals(QuoteState.PLACED_ORDERED, quote.getState()); }
   */

  /*
   * //TODO SOUFIANE
   * 
   * @Test
   * 
   * @DisplayName("test set start date with a good id") public void testSetStartDateQuoteInDbOk()
   * throws FatalException, BizException { QuoteDto quoteDto = dtoFactory.getQuote();
   * quoteDto.setIdQuote("setDate"); quoteDto.setStartDate(LocalDate.now());
   * 
   * QuoteDto quoteToTest = qcc.setStartDateQuoteInDb(quoteDto);
   * assertEquals(QuoteState.CONFIRMED_DATE, quoteToTest.getState()); }
   * 
   * 
   * @Test
   * 
   * @DisplayName("test set start date with a bad id") public void testSetStartDateQuoteInDbKo()
   * throws FatalException, BizException { QuoteDto quoteDto = dtoFactory.getQuote();
   * quoteDto.setIdQuote("ko"); quoteDto.setStartDate(LocalDate.now());
   * 
   * QuoteDto quoteToTest = qcc.setStartDateQuoteInDb(quoteDto);
   * assertNotEquals(QuoteState.CONFIRMED_DATE, quoteToTest.getState()); }
   */

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
}
