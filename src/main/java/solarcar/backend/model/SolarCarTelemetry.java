package solarcar.backend.model;

public record SolarCarTelemetry(float mainPackCurrent, float mainPackInstVolt, float mainPackAmps, float mainPackSummedV,float packLowTemp, float packHighTemp,float packAvgTemp,float packHiCellV, float packLoCellV, float packAvgCellV,int packHiCellID, int packLoCellID, float dcBusCurrent,float dcBusVoltage, float velocity, float velMotorRPM, float amphours, float motorCurrent, float cmdMotorRPM, float gpsLong,float gpsLang) {
}