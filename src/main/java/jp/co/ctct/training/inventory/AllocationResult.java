package jp.co.ctct.training.inventory;

/**
 * 在庫引当の結果。
 *
 * @param allocated 引当に成功したか
 * @param item      引当後の商品 (失敗時は引当前のまま)
 * @param message   結果の説明 (演習でログ・テストレポートから読み取る対象)
 */
public record AllocationResult(boolean allocated, Item item, String message) {

    static AllocationResult success(Item after, int quantity) {
        return new AllocationResult(true, after, "引当成功: " + quantity + " 個 (残り在庫 " + after.stock() + ")");
    }

    static AllocationResult failure(Item item, int quantity) {
        return new AllocationResult(false, item, "在庫不足: 要求 " + quantity + " 個に対して在庫 " + item.stock() + " 個");
    }
}
