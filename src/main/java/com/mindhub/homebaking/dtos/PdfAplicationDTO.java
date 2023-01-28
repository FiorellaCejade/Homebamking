package com.mindhub.homebaking.dtos;

import java.time.LocalDateTime;

public class PdfAplicationDTO {

    private String account;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    public PdfAplicationDTO(String account, LocalDateTime dateStart, LocalDateTime dateEnd) {
        this.account = account;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getAccount() {
        return account;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }
}
