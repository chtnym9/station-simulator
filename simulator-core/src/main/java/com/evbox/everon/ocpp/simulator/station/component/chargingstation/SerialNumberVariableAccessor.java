package com.evbox.everon.ocpp.simulator.station.component.chargingstation;

import com.evbox.everon.ocpp.common.CiString;
import com.evbox.everon.ocpp.simulator.station.Station;
import com.evbox.everon.ocpp.simulator.station.StationHardwareData;
import com.evbox.everon.ocpp.simulator.station.component.variable.SetVariableValidator;
import com.evbox.everon.ocpp.simulator.station.component.variable.VariableAccessor;
import com.evbox.everon.ocpp.simulator.station.component.variable.VariableGetter;
import com.evbox.everon.ocpp.simulator.station.component.variable.VariableSetter;
import com.evbox.everon.ocpp.v20.message.centralserver.*;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.Map;

public class SerialNumberVariableAccessor extends VariableAccessor {

    public static final String NAME = "SerialNumber";

    private final Map<GetVariableDatum.AttributeType, VariableGetter> variableGetters = ImmutableMap.<GetVariableDatum.AttributeType, VariableGetter>builder()
            .put(GetVariableDatum.AttributeType.ACTUAL, this::getActualValue)
            .build();

    private final Map<SetVariableDatum.AttributeType, SetVariableValidator> variableValidators = ImmutableMap.<SetVariableDatum.AttributeType, SetVariableValidator>builder()
            .put(SetVariableDatum.AttributeType.ACTUAL, this::validateActualValue)
            .build();

    public SerialNumberVariableAccessor(Station station) {
        super(station);
    }

    @Override
    public String getVariableName() {
        return NAME;
    }

    @Override
    public Map<GetVariableDatum.AttributeType, VariableGetter> getVariableGetters() {
        return variableGetters;
    }

    @Override
    public Map<SetVariableDatum.AttributeType, VariableSetter> getVariableSetters() {
        return Collections.emptyMap();
    }

    @Override
    public Map<SetVariableDatum.AttributeType, SetVariableValidator> getVariableValidators() {
        return variableValidators;
    }

    private SetVariableResult validateActualValue(Component component, Variable variable, SetVariableDatum.AttributeType attributeType, CiString.CiString1000 attributeValue) {
        return READ_ONLY_VALIDATOR.validate(component, variable, attributeType, attributeValue);
    }

    private GetVariableResult getActualValue(Component component, Variable variable, GetVariableDatum.AttributeType attributeType) {
        return new GetVariableResult()
                .withComponent(component)
                .withVariable(variable)
                .withAttributeType(GetVariableResult.AttributeType.fromValue(attributeType.value()))
                .withAttributeValue(new CiString.CiString1000(StationHardwareData.SERIAL_NUMBER))
                .withAttributeStatus(GetVariableResult.AttributeStatus.ACCEPTED);
    }

}
