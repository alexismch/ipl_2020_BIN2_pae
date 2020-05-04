package be.ipl.pae.biz.objets;

import static be.ipl.pae.biz.objets.QuoteState.CANCELLED;
import static be.ipl.pae.biz.objets.QuoteState.CONFIRMED_DATE;
import static be.ipl.pae.biz.objets.QuoteState.PARTIAL_INVOICE;
import static be.ipl.pae.biz.objets.QuoteState.PLACED_ORDERED;
import static be.ipl.pae.biz.objets.QuoteState.QUOTE_ENTERED;
import static be.ipl.pae.biz.objets.QuoteState.TOTAL_INVOICE;
import static be.ipl.pae.biz.objets.QuoteState.VISIBLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class QuoteStateTest {

  static Stream<Arguments> quoteStates() {
    Arguments[] arguments = new Arguments[]{
        Arguments.of(QUOTE_ENTERED, 1),
        Arguments.of(PLACED_ORDERED, 2),
        Arguments.of(CONFIRMED_DATE, 3),
        Arguments.of(PARTIAL_INVOICE, 5),
        Arguments.of(TOTAL_INVOICE, 6),
        Arguments.of(VISIBLE, 7),
        Arguments.of(CANCELLED, 8),
    };

    return Stream.of(arguments);
  }

  @DisplayName("Test if id and QuoteState match")
  @ParameterizedTest
  @MethodSource("quoteStates")
  void quoteStateId(QuoteState quoteState, int id) {
    assertEquals(id, quoteState.getId());
    assertEquals(quoteState, QuoteState.getById(id));
  }
}