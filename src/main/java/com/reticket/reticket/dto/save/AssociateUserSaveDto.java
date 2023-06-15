package com.reticket.reticket.dto.save;

public class AssociateUserSaveDto {

    private boolean isTheaterAdmin;
    private Long theaterId;
    private GuestUserSaveDto guestUserSaveDto;

    public AssociateUserSaveDto(boolean isTheaterAdmin, Long theaterId, GuestUserSaveDto guestUserSaveDto) {
        this.isTheaterAdmin = isTheaterAdmin;
        this.theaterId = theaterId;
        this.guestUserSaveDto = guestUserSaveDto;
    }

    public boolean isTheaterAdmin() {
        return isTheaterAdmin;
    }

    public void setTheaterAdmin(boolean theaterAdmin) {
        isTheaterAdmin = theaterAdmin;
    }

    public Long getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Long theaterId) {
        this.theaterId = theaterId;
    }

    public GuestUserSaveDto getGuestUserSaveDto() {
        return guestUserSaveDto;
    }

    public void setGuestUserSaveDto(GuestUserSaveDto guestUserSaveDto) {
        this.guestUserSaveDto = guestUserSaveDto;
    }
}
