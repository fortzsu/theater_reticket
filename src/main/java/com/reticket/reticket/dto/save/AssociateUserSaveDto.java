package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssociateUserSaveDto {

    private boolean isTheaterAdmin;
    private Long theaterId;
    private GuestUserSaveDto guestUserSaveDto;

    public AssociateUserSaveDto(boolean isTheaterAdmin, Long theaterId, GuestUserSaveDto guestUserSaveDto) {
        this.isTheaterAdmin = isTheaterAdmin;
        this.theaterId = theaterId;
        this.guestUserSaveDto = guestUserSaveDto;
    }


}
