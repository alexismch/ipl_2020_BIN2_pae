package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class CustomerUccImplTest {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private CustomerUcc custcc;

  private CustomerDto custo;

  private CustomersFilterDto custoFilt;

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

    custo = dtoFactory.getCustomer();
    custo.setIdCustomer(1);
    custo.setCity("Bruxelles");
    custo.setAddress("rue du pre");
    custo.setEmail("toto@gmail.com");
    custo.setFirstName("john");
    custo.setLastName("Doe");
    custo.setPhoneNumber("0489895774");
    custo.setPostalCode(1140);

    custoFilt = dtoFactory.getCustomersFilter();
  }

  @DisplayName("ucc test different from null")
  @Test
  public void testUcc() {
    assertNotNull(custcc);
  }

  @DisplayName("list not null")
  @Test
  public void testGetCustomers1() {
    assertNotNull(custcc.getCustomers(custoFilt));
  }

  @DisplayName("list not empty without filter")
  @Test
  public void testGetCustomers2() {
    assertTrue(custcc.getCustomers(custoFilt).size() > 0);
  }

  @DisplayName("filter null")
  @Test
  public void testGetCustomers3() {
    assertNotNull(custcc.getCustomers(null));
  }

  @DisplayName("with a filter")
  @Test
  public void testGetCustomers4() {
    custoFilt.setName("Bernard");
    assertNotNull(custcc.getCustomers(custoFilt));
  }

  /*
   * @DisplayName("ucc test different from null")
   *
   * @Test public void testInsertCustomer1() { custo.setFirstName("");
   * assertThrows(BizException.class, () -> custcc.insert(custo)); }
   *
   * @DisplayName("ucc test different from null")
   *
   * @Test public void testInsertCustomer2() { custo.setLastName("");
   * assertThrows(BizException.class, () -> custcc.insert(custo)); }
   *
   * @DisplayName("ucc test different from null")
   *
   * @Test public void testInsertCustomer3() { custo.setCity(""); assertThrows(BizException.class,
   * () -> custcc.insert(custo)); }
   *
   * @DisplayName("ucc test different from null")
   *
   * @Test public void testInsertCustomer4() { custo.setEmail(""); assertThrows(BizException.class,
   * () -> custcc.insert(custo)); }
   *
   * @DisplayName("ucc test different from null")
   *
   * @Test public void testInsertCustomer5() { custo.setPhoneNumber("");
   * assertThrows(BizException.class, () -> custcc.insert(custo)); }
   */


}

