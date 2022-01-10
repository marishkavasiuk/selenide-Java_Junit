package myframework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Membership {

    private Boolean deactivated;
    private String id;
    private String idMember;
    private String memberType;
    private Boolean unconfirmed;

}
