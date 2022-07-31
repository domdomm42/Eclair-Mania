Planning: 2.5/3
- Planning is more of an entity hierarchy than a dependency tree
- Should be made around functionality, not implementation (e.g. having a ticket for an interface on its own is problematic)

Ticket Lifecycle: 2/5
- Reasonably good ticket lifecycle
- TDD wrong way around, need to test on a ticket basis rather than writing all tests up front
- Failing CI a lot - need a passing pipeline

Collaborative Engineering: 2/2

Other feedback

```java
if ((goals.toString().contains(":treasure") && !completedGoals.contains(":treasure")) 
        || (goals.toString().contains(":boulders") && !completedGoals.contains(":boulders")) 
        || (goals.toString().contains(":enemies") && !completedGoals.contains(":enemies"))) completedGoals.remove(":exit");
        else if (getFirstEntityOfTypeOnPosition(getPlayer().getPosition(), "exit") != null) completedGoals.add(":exit");
        else completedGoals.remove(":exit");
```

Not open-closed here- think about how to make more extensible
- Lots of long lines/methods on that indicate tight coupling

```java
@Override
    public void move(Direction direction) {
        Player player = (Player) getEntity();
        if (player == null) return;
        Position requestedPosition = player.getPositionInDirection(direction);
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(requestedPosition);
        if (Dungeon.isEntityOnPosition(requestedPosition, "portal")) {
            Portal portal = (Portal) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "portal");
            requestedPosition = portal.getTeleportLocation(direction);
        }
        if (Dungeon.isEntityOnPosition(requestedPosition, "door")) {
            Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "door");
            if (!door.isUnlocked()) {
                if (player.getInventory("key").stream().filter(entity -> door.getKeyThatUnlock() != null && door.getKeyThatUnlock().equals(entity)).findFirst().isEmpty()) return;
                else {
                    player.useKey(door.getKeyThatUnlock());
                    door.setUnlocked(true);
                }
            }
        }
        if (Dungeon.isEntityOnPosition(requestedPosition, "wall")) return;
        if (Dungeon.isEntityOnPosition(requestedPosition, "boulder")) {
            ((Boulder) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "boulder")).getMovementStrategy().move(direction);
            if (Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "boulder") != null) return;
        } 
        player.setLastPosition(player.getPosition());
        player.setPosition(requestedPosition);
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Enemy) && !player.activePotionEffect().equals("invisibility_potion")) {
            if (Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "mercenary") != null && ((Mercenary) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "mercenary")).isAlly()) return;
            Dungeon.addBattle(new Battle(player, (Enemy) entitiesOnPosition.stream().filter(entity -> entity instanceof Enemy).findFirst().map(entity -> {
                return entity;
            }).orElse(null)));
            return;
        }
        entitiesOnPosition.stream().filter(entity -> entity instanceof CollectableEntity).forEach(entity -> {
            CollectableEntity collectableEntity = (CollectableEntity) entity;
            if (collectableEntity instanceof Bomb) {
                if (((Bomb) collectableEntity).isHasBeenPickedUp()) {
                    return;
                }
            }
            player.pickup((CollectableEntity) entity);
            collectableEntity.setPickedUp(true);
        });
    }
```

Use polymorphism and delegation to your advantage to reduce the spaghetti code here.

- Difficult to discern where you have implemented a state pattern
- Nice testing plan, however need to think about unit-level tests on the classes as well as broader system tests on the controller
- Good test code

Modelling of Entities: 10/10
Coupling & Cohesion: 5/10
Design Patterns: 3/5
Test Design - Structure: 3/5
Test Design - Quality: 5/5
Code Quality: 2/5