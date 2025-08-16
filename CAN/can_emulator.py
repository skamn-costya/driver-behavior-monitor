import can
import time
import numpy as np

# Настройки режима: city, highway, mix
MODE = "city"

# Частота обновления данных (Гц)
FREQ = 10

# Интерфейс CAN
bus = can.interface.Bus(channel='vcan0', interface='socketcan')

# Начальные значения
rpm = 800
fuel = 100
gear = 1
speed = 0
temp = 70

# Режимные параметры
mode_params = {
    "city": {"rpm_range": (800, 3000), "speed_range": (0, 50)},
    "highway": {"rpm_range": (1500, 5000), "speed_range": (50, 120)},
    "mix": {"rpm_range": (800, 4500), "speed_range": (0, 100)},
}

def smooth_random(prev, min_val, max_val, max_delta):
    """Генерирует плавное случайное изменение"""
    delta = np.random.uniform(-max_delta, max_delta)
    new_val = prev + delta
    return max(min(new_val, max_val), min_val)

while True:
    params = mode_params[MODE]
    rpm = smooth_random(rpm, *params["rpm_range"], 200)
    speed = smooth_random(speed, *params["speed_range"], 5)
    fuel = max(fuel - np.random.uniform(0, 0.01), 0)
    temp = smooth_random(temp, 70, 100, 0.5)
    gear = max(1, min(6, int(speed / 20) + 1))

    # Создаём CAN-сообщения
    bus.send(can.Message(arbitration_id=0x100, data=[int(rpm/256), int(rpm%256)], is_extended_id=False))
    bus.send(can.Message(arbitration_id=0x101, data=[int(fuel), gear, int(speed), int(temp)], is_extended_id=False))

    time.sleep(1/FREQ)
