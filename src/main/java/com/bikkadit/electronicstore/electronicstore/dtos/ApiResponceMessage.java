package com.bikkadit.electronicstore.electronicstore.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponceMessage {

    private String message;
    private Boolean success;
    private HttpStatus status;

}
