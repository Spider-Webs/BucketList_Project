package bucket.list.memberdto;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AddressEmbed implements Serializable {

    private String zipcode;
    private String streetAdr;
    private String detailAdr;

    public AddressEmbed(){

    }

    public AddressEmbed(String zipcode, String streetAdr, String detailAdr) {
        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
    }

}
