class WeatherConditions:

  def __init__(self, temperature, weatherCode, windSpeed):
    self.temperature = temperature
    self.weatherCode = weatherCode
    self.windSpeed = windSpeed

  @staticmethod
  def from_json(json):
    return WeatherConditions(json['temperature'], json['weatherCode'], json['windSpeed'])
