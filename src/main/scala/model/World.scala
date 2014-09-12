package model

/**
 * Этот класс описывает игровой мир. Содержит также описания всех игроков и игровых объектов (<<юнитов>>).
 */
class World(tick: Int, tickCount: Int, width: Double, height: Double,
            players: Vector[Option[Player]], hockeyists: Vector[Option[Hockeyist]], puck: Option[Puck])
{
  /**
   * @return Возвращает номер текущего тика.
   */
  def getTick: Int = tick

  /**
   * @return Возвращает базовую длительность игры в тиках.
   *         Реальная длительность может отличаться от этого значения в большую сторону.
   */
  def getTickCount: Int = tickCount

  /**
   * @return Возвращает ширину мира.
   */
  def getWidth: Double = width

  /**
   * @return Возвращает высоту мира.
   */
  def getHeight: Double = height

  /**
   * @return Возвращает список игроков (в случайном порядке).
   *         После каждого тика объекты, задающие игроков, пересоздаются.
   */
  def getPlayers: Vector[Option[Player]] = players

  /**
   * @return Возвращает список хоккеистов (в случайном порядке), включая вратарей и хоккеиста стратегии,
   *         вызвавшей этот метод. После каждого тика объекты, задающие хоккеистов, пересоздаются.
   */
  def getHockeyists: Vector[Option[Hockeyist]] = hockeyists

  /**
   * @return Возвращает шайбу.
   */
  def getPuck: Option[Puck] = puck

  /**
   * @return Возвращает вашего игрока.
   */
  def getMyPlayer: Option[Player] = players.flatten.find(_.isMe)
     

  /**
   * @return Возвращает игрока, соревнующегося с вами.
   */
  def getOpponentPlayer: Option[Player] = players.flatten.filterNot(_.isMe).headOption
}

