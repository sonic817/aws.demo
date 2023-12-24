package aws.demo.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloResponseDto {
    private  String name;
    private  int amount;
}