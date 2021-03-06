package model

/**
 * Возможные действия хоккеиста.
 * <p/>
 * Хоккеист может совершить действие, если он не сбит с ног ([[model.HockeyistState.KnockedDown]]),
 * не отдыхает ([[model.HockeyistState.Resting]]) и уже восстановился после своего предыдущего действия
 * (значение [[model.Hockeyist#getRemainingCooldownTicks]] равно `0`).
 * <p/>
 * Если хоккеист замахивается клюшкой ([[model.HockeyistState.Swinging]]), то из действий ему доступны только
 * [[model.ActionType.Strike]] и [[model.ActionType.CancelStrike]].
 */
sealed trait ActionType

object ActionType {

  /**
   * Ничего не делать.
   */
  case object None extends ActionType

  /**
   * Взять шайбу.
   * <p/>
   * Если хоккеист уже контролирует шайбу, либо шайба находится вне зоны досягаемости клюшки хоккеиста (смотрите
   * документацию к значениям [[model.Game#getStickLength]] и [[model.Game#getStickSector]]), то действие игнорируется.
   * <p/>
   * В противном случае хоккеист попытается установить контроль над шайбой и, с определённой вероятностью,
   * это сделает ((смотрите документацию к [[model.Game#getPickUpPuckBaseChance]]
   * и [[model.Game#getTakePuckAwayBaseChance]])).
   */
  case object TakePuck extends ActionType

  /**
   * Замахнуться для удара.
   * <p/>
   * Хоккеист замахивается для увеличения силы удара. Чем больше тиков пройдёт с момента начала замаха до удара,
   * тем большее воздействие будет на попавшие под удар объекты. Максимальное количество учитываемых тиков ограничено
   * значением [[model.Game#getMaxEffectiveSwingTicks]].
   */
  case object Swing extends ActionType

  /**
   * Ударить.
   * <p/>
   * Хоккеист наносит размашистый удар по всем объектам, находящимся в зоне досягаемости его клюшки. Удар может быть
   * совершён как с предварительным замахом ([[model.ActionType.Swing]]), так и без него (в этом случае сила удара
   * будет меньше).
   * <p/>
   * Объекты (шайба и хоккеисты, кроме вратарей), попавшие под удар, приобретут некоторый импульс в направлении,
   * совпадающим с направлением удара. При ударе по хоккеисту есть также некоторый шанс сбить его с ног.
   */
  case object Strike extends ActionType

  /**
   * Отменить удар.
   * <p/>
   * Хоккеист выходит из состояния замаха ([[model.ActionType.Swing]]), не совершая удар. Это позволяет
   * сэкономить немного выносливости, а также быстрее совершить новое действие
   * (смотрите документацию к [[model.Game#getCancelStrikeActionCooldownTicks]]).
   * <p/>
   * Если хоккеист не совершает замах клюшкой, то действие игнорируется.
   */
  case object CancelStrike extends ActionType

  /**
   * Отдать пас.
   * <p/>
   * Хоккеист пытается передать контролируемую им шайбу другому хоккеисту. Для этого необходимо указать относительную
   * силу паса ([[model.Move#setPassPower]]) и его направление ([[model.Move#setPassAngle]]). В противном случае пас
   * будет отдан в направлении, соответствующем направлению хоккеиста, с максимально возможной силой.
   * <p/>
   * Если хоккеист не контролирует шайбу, то действие игнорируется.
   */
  case object Pass extends ActionType

  /**
   * Заменить активного хоккеиста сидящим на скамейке запасных.
   * <p/>
   * Замена выполняется только на своей половине поля, при этом расстояние от центра хоккеиста до верхней границы
   * игровой площадки не должно превышать [[model.Game#getSubstitutionAreaHeight]]. Дополнительно нужно указать индекс
   * хоккеиста ([[model.Move#setTeammateIndex]]), на которого будет произведена замена.
   * <p/>
   * Если указан некорректный индекс, или скорость хоккеиста превышает [[model.Game#getMaxSpeedToAllowSubstitute]],
   * то действие будет проигнорировано.
   */
  case object Substitute extends ActionType

  /**
   * Ни одно из известных действий.
   */
  case object Unknown extends ActionType
}
