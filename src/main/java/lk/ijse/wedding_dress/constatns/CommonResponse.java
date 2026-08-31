package lk.ijse.wedding_dress.constatns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResponse {

    private int status;
    private Object body;
    private String message;
}