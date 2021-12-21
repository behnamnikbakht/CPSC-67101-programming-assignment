package ca.ucalgary.assignment.service.dto;

import ca.ucalgary.assignment.domain.Sell;

public class AvailableNotifyEvent {

    private Sell item;

    public AvailableNotifyEvent() {}

    public AvailableNotifyEvent(Sell item) {
        this.item = item;
    }

    public Sell getItem() {
        return item;
    }

    public void setItem(Sell item) {
        this.item = item;
    }

    public AvailableNotifyEvent item(Sell item) {
        this.item = item;
        return this;
    }

    @Override
    public String toString() {
        return "AvailableNotifyEvent{" + "item=" + item + '}';
    }
}
