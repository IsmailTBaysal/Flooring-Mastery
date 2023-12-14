package org.example.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {
    void print(String msg);
    String readString(String prompt);
    int  readInt(String msgPrompt);
    int readInt(String msgPrompt, int min, int max);
    BigDecimal readBigDecimal(String msgPrompt);
    LocalDate readLocalDate(String msgPrompt);
}
