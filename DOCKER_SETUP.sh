#!/bin/bash
echo "
===============================================================================================================================================
===============================================================================================================================================

"
echo "
                     ____   ___  _        _    ____     ____    _    ____      _____ _____ _     _____ __  __ _____ _____ ______   __
                    / ___| / _ \| |      / \  |  _ \   / ___|  / \  |  _ \    |_   _| ____| |   | ____|  \/  | ____|_   _|  _ \ \ / /
                    \___ \| | | | |     / _ \ | |_) | | |     / _ \ | |_) |     | | |  _| | |   |  _| | |\/| |  _|   | | | |_) \ V / 
                     ___) | |_| | |___ / ___ \|  _ <  | |___ / ___ \|  _ <      | | | |___| |___| |___| |  | | |___  | | |  _ < | |  
                    |____/ \___/|_____/_/   \_\_| \_\  \____/_/   \_\_| \_\     |_| |_____|_____|_____|_|  |_|_____| |_| |_| \_\|_|  
                                                                    
                                                                    
                                                                    
                                                                    #############+                                                                        
                                                                ##        :++**######                  .+%#####.                                          
                                                             *#      ==+++++*########@  #%############################%                                   
                                                           .# :   ===+++++*%%%%%%%%@%%########################################@                           
                                                          #*::::=#############%%%%%%%#################################################.                   
                                                      @##%%==-=++++**#########%%%@##########################################@@@@@                         
                                             @##########%#****++****######%#####################################@@@@@@@@@                                 
                                     #################################################################@@@@@@@@@@                                          
                              @#############################################################%@@@@@@@@@@@@                                                 
                        @###########################################################@@@@@@@@@@@@@@@@.                                                     
                  %########################################################%@@@@@@@@@@@@@@@@@@@@@@@                                                       
             %######################################################@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                          
             #################################################@@@@@@@@@@@@@%%@@@@@@@@@@@@@@@%                                                             
                 @#####################################%@@@@@@@@@@@%%%%%@@@@@@@@@@@@@@@@@                                                                 
                     @###########################*@@@@@@@@@@@@:@@@@@@@@@@@@@@@@@@@@@@                                                                     
                         ###################%@@@@@@@@@@@@@@@@+@@@@@@@@@@@@@@@@@@@                                                                         
                          -@@##########@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                                              
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@.                                                                                  
"
echo "
===============================================================================================================================================
===============================================================================================================================================

"

echo "------------------------------------"
echo "|    Starting Docker Containers    |"
echo "------------------------------------"

# Start only the influx docker container
docker compose up -d influxdb3-core

# Retrieve the admin key and place it in our .env file
docker exec influxdb influxdb3 create token --admin | grep -oP -m 1 "apiv3\S*" >> ./apikey.env

# Re-run docker compose but for the whole application 
docker compose up -d

echo "------------------------------------"
echo "|        Setting Up Influx3        |"
echo "------------------------------------"



# Create our DB
docker exec influxdb influxdb3 create database \
    --retention-period 90d \
    SOLARCAR


# Create our table
docker exec influxdb influxdb3 create table \
    --tags location \
    --fields mainPackCurrent:float64,mainPackInstVolt:float64,mainPackAmps:float64,mainPackSummedV:float64,packLowTemp:float64,packHighTemp:float64,packAvgTemp:float64,packHiCellV:float64,packLoCellV:float64,packAvgCellV:float64,packHiCellID:int64,packLoCellID:int64,dcBusCurrent:float64,dcBusVoltage:float64,velocity:float64,velMotorRPM:float64,amphours:float64,motorCurrent:float64,cmdMotorRPM:float64\
    --database SOLARCAR \
    TELEMETRY

# Create our LVC
docker exec influxdb influxdb3 create last_cache \
    --database SOLARCAR \
    --table TELEMETRY \
    --key-columns location \
    --count 1 \
    --ttl 10000h \
    TELEMETRY_LAST_CACHE



echo "------------------------------------"
echo "|             Complete             |"
echo "------------------------------------"


# See https://github.com/influxdata/helm-charts/issues/781
# WARN influxdb3::env_compat: environment variable LOG_FILTER is deprecated, use INFLUXDB3_LOG_FILTER instead


