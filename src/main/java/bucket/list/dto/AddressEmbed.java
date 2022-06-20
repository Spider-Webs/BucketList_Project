package bucket.list.dto;

import javax.persistence.Embeddable;

@Embeddable
public class AddressEmbed {

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
