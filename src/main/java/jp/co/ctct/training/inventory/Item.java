package jp.co.ctct.training.inventory;

/**
 * 在庫管理の対象となる商品。
 *
 * @param id           商品 ID (空文字不可)
 * @param name         商品名 (空文字不可)
 * @param stock        現在の在庫数 (0 以上)
 * @param reorderPoint 発注点。在庫数がこの値以下になったら発注が必要 (0 以上)
 */
public record Item(String id, String name, int stock, int reorderPoint) {

    public Item {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("商品 ID は必須です");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("商品名は必須です");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("在庫数は 0 以上で指定してください: " + stock);
        }
        if (reorderPoint < 0) {
            throw new IllegalArgumentException("発注点は 0 以上で指定してください: " + reorderPoint);
        }
    }

    /** 在庫数だけを変更した新しい Item を返す (record は不変のため)。 */
    public Item withStock(int newStock) {
        return new Item(id, name, newStock, reorderPoint);
    }
}
