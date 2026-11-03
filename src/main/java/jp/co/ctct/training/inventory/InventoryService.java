package jp.co.ctct.training.inventory;

/**
 * 在庫の引当と発注判定を行うサービス。
 *
 * <p>本研修の CI 演習のテスト対象。境界値 (在庫と同数の引当、発注点ちょうどの在庫) の
 * 挙動をテストコードで固定している。
 */
public class InventoryService {

    /**
     * 在庫を引き当てる。
     *
     * <p>在庫数と同数までの引当は成功する (引当後の在庫は 0 になり得る)。
     *
     * @param item     対象商品
     * @param quantity 引当数量 (1 以上)
     * @return 引当結果。在庫不足の場合は失敗となり在庫は変化しない
     */
    public AllocationResult allocate(Item item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("引当数量は 1 以上で指定してください: " + quantity);
        }
        if (item.stock() >= quantity) {
            int remaining = item.stock() - quantity;
            return AllocationResult.success(item.withStock(remaining), quantity);
        }
        return AllocationResult.failure(item, quantity);
    }

    /**
     * 発注が必要かを判定する。
     *
     * <p>在庫数が発注点「以下」になったら発注が必要 (発注点ちょうどを含む)。
     */
    public boolean needsReorder(Item item) {
        return item.stock() <= item.reorderPoint();
    }

    /**
     * 目標在庫まで戻すための発注数量を計算する。
     *
     * @param item        対象商品
     * @param targetStock 目標在庫数 (発注点より大きいこと)
     * @return 発注数量。在庫が目標以上ある場合は 0
     */
    public int reorderQuantity(Item item, int targetStock) {
        if (targetStock <= item.reorderPoint()) {
            throw new IllegalArgumentException(
                    "目標在庫数は発注点より大きい値を指定してください: 目標 " + targetStock + " / 発注点 " + item.reorderPoint());
        }
        return Math.max(0, targetStock - item.stock());
    }
}
