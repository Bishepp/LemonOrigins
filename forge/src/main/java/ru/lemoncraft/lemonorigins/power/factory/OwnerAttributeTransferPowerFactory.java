package ru.lemoncraft.lemonorigins.power.factory;

import ru.lemoncraft.lemonorigins.power.OwnerAttributeTransferPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;

public class OwnerAttributeTransferPowerFactory extends PowerFactory<OwnerAttributeTransferPower> {

    public OwnerAttributeTransferPowerFactory() {
        super(OwnerAttributeTransferPower.CODEC);
    }
}