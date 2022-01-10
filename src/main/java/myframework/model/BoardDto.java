package myframework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {

    private Boolean closed;
    private Object creationMethod;
    private String dateClosed;
    private Object dateLastActivity;
    private String dateLastView;
    private Object datePluginDisable;
    private String desc;
    private Object descData;
    private Boolean enterpriseOwned;
    private String id;
    private Object idBoardSource;
    private Object idEnterprise;
    private String idMemberCreator;
    private String idOrganization;
    private List<Object> idTags;
    private Object ixUpdate;
    private Object limits;
    private List<Membership> memberships;
    private String name;
    private Object pinned;
    private List<Object> powerUps;
    private List<String> premiumFeatures;
    private String shortLink;
    private String shortUrl;
    private Boolean starred;
    private Boolean subscribed;
    private Object templateGallery;
    private String url;
}
