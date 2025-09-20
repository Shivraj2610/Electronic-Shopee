package com.shiv.electronic.store.custome;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUploadedResponse {

    private String fileName;
    private String message;
    private boolean success;
    private HttpStatus status;
}
