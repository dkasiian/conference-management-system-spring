package com.dkasiian.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class ErrorResponse {

    private int status;
    private String message;
    private Long timeStamp;

}
