package jp.co.ctct.training.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("発注判定 (needsReorder / reorderQuantity)")
class InventoryServiceReorderTest {

    private final InventoryService service = new InventoryService();

    private Item item(int stock, int reorderPoint) {
        return new Item("ITEM-002", "発注テスト商品", stock, reorderPoint);
    }

    @Test
    @DisplayName("在庫が発注点より多いとき、発注は不要")
    void noReorderWhenStockAboveReorderPoint() {
        assertFalse(service.needsReorder(item(6, 5)));
    }

    @Test
    @DisplayName("在庫が発注点ちょうどのとき、発注が必要 (境界値)")
    void reorderNeededAtReorderPointBoundary() {
        assertTrue(service.needsReorder(item(5, 5)));
    }

    @Test
    @DisplayName("在庫が発注点を下回るとき、発注が必要")
    void reorderNeededWhenStockBelowReorderPoint() {
        assertTrue(service.needsReorder(item(4, 5)));
    }

    @Test
    @DisplayName("発注数量は目標在庫と現在庫の差になる")
    void reorderQuantityIsDifferenceToTarget() {
        assertEquals(8, service.reorderQuantity(item(2, 5), 10));
    }

    @Test
    @DisplayName("在庫が目標以上あるとき、発注数量は 0")
    void reorderQuantityIsZeroWhenStockMeetsTarget() {
        assertEquals(0, service.reorderQuantity(item(12, 5), 10));
    }

    @Test
    @DisplayName("目標在庫が発注点以下なら IllegalArgumentException")
    void reorderQuantityRejectsTargetNotAboveReorderPoint() {
        assertThrows(IllegalArgumentException.class, () -> service.reorderQuantity(item(2, 5), 5));
    }
}
