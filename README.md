# ShopGUIReborn

WIP

```yml
# Shops.yml
catalog:
  name: '&c&lCategories'
  rows: 1
  categories:
    food:
      category_item:
        material: APPLE
        quantity: 1
        name: '&cFood'
        lore:
        - '&tThis is the &qfood&t category!'
        - '&tFood is &qdelicious&t!'
        item_flags:
        - HIDE_ENCHANTS
        enchants:
          luck_of_the_sea:
            level: 3
        slot: 1
      category:
        name: "&c&lFood"
        rows: 3
        command: food
        items:
          '1':
            keep-open: false
            material: BREAD
            quantity: 2
            name: '&qBread'
            lore:
            - '&tBread is &qgood&t.'
            - '&tBread is &qlife&t.'
            price:
              xp-levels: 1
              buy: 10
              sell: 5
            slot: 1
    blocks:
      category_item:
        material: GRASS_BLOCK
        name: '&2Grass'
        lore:
        - '&tGrass category.'
        slot: 2
      category:
        name: '&2&lGrass'
        rows: 2
        items:
          '1':
            material: GRASS_BLOCK
            price:
              buy: 2
              sell: 1
            slot: 1
          '2':
            material: STONE
            price:
              buy: 4
              sell: 2
            slot: 11
```

```yml
# Buttons.yml
buttons:
  buy_sell_inventory:
    name: "&cBuy Sell"
    rows: 5
    product:
      slot: 14
  remove1:
    material: BLACK_STAINED_GLASS_PANE
    name: "&a&lRemove 1"
    slot: 3
  remove10:
    material: BLACK_STAINED_GLASS_PANE
    quantity: 10
    name: "&a&lRemove 10"
    slot: 11
  set1:
    material: BLACK_STAINED_GLASS_PANE
    name: "&a&lSet to 1"
    slot: 19
  add1:
    material: BLACK_STAINED_GLASS_PANE
    name: "&a&lAdd 1"
    slot: 7
  add10:
    material: BLACK_STAINED_GLASS_PANE
    quantity: 10
    name: "&a&lAdd 10"
    slot: 17
  set64:
    material: BLACK_STAINED_GLASS_PANE
    quantity: 64
    name: "&a&lSet to 64"
    slot: 27
  sellall:
    material: BLACK_STAINED_GLASS_PANE
    name: "&a&lSell All"
    slot: 33
  confirm:
    material: PINK_STAINED_GLASS_PANE
    name: "&d&lConfirm"
    slot: 41
  cancel:
    material: BLACK_STAINED_GLASS_PANE
    name: "&a&lCancel"
    slot: 31
  back:
    material: COAL
    name: "&cGo Back"
    slots_from_bottom: 5
```

```yml
# Global.yml
global_hide_flags: []
auto-refresh: 5
open_shop_item:
  enabled: true
  recieved_on_join: true
  right_click: true
  left_click: true
  item:
    material: STICK
    name: '&qOpen Shop'
color:
  primary: '&f'
  secondary: '&8'
  tertiary: '&7'
  start_color: '&7'
```

```yml
# Messages.yml
messages:
  sell:
    message: Item was sold.
    enabled: true
  buy:
    message: Item was bought.
    enabled: true
  no_permission:
    message: No permission.
    enabled: true
  opening_shop:
    message: Opening shop..
    enabled: true
  editing_shop:
    message: Editing shop..
    enabled: true
  help:
    message: "&w[&qEpicShop Help&w]&t\n \n/shop\n/shop help\n/shop edit"
  error:
    message: Error
```

```yml
# Permissions.yml
open_shop:
  node: epicshop.open
  default: true
```

```yml
# Signs.yml
signs:
  '1':
    name: '&3Food Shop'
    shop: food
```
