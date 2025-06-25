package coder.himakara.Set_my_goals.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer department;
    private String jobTitle;
    private Integer manager;

}
