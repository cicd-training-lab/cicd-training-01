package jp.co.ctct.training.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("在庫引当 (allocate)")
class InventoryServiceAllocateTest {

    private final InventoryService service = new InventoryService();

    private Item item(int stock) {
        return new Item("ITEM-001", "テスト商品", stock, 5);
    }

    @Test
    @DisplayName("在庫が十分にあるとき、引当は成功して在庫が減る")
    void allocateSucceedsWhenStockIsEnough() {
        AllocationResult result = service.allocate(item(10), 3);
        assertTrue(result.allocated());
        assertEquals(7, result.item().stock());
    }

    @Test
    @DisplayName("在庫と同数の引当は成功し、在庫は 0 になる (境界値)")
    void allocateSucceedsWhenQuantityEqualsStock() {
        AllocationResult result = service.allocate(item(10), 10);
        assertTrue(result.allocated());
        assertEquals(0, result.item().stock());
    }

    @Test
    @DisplayName("在庫を超える引当は失敗し、在庫は変化しない")
    void allocateFailsWhenQuantityExceedsStock() {
        AllocationResult result = service.allocate(item(10), 11);
        assertFalse(result.allocated());
        assertEquals(10, result.item().stock());
    }

    @Test
    @DisplayName("在庫 0 のとき、1 個の引当も失敗する (境界値)")
    void allocateFailsWhenStockIsZero() {
        AllocationResult result = service.allocate(item(0), 1);
        assertFalse(result.allocated());
        assertEquals(0, result.item().stock());
    }

    @Test
    @DisplayName("引当 1 個は在庫 1 のとき成功する (最小の成功ケース)")
    void allocateSucceedsAtMinimumBoundary() {
        AllocationResult result = service.allocate(item(1), 1);
        assertTrue(result.allocated());
        assertEquals(0, result.item().stock());
    }

    @Test
    @DisplayName("失敗時のメッセージに要求数と在庫数が含まれる")
    void failureMessageContainsQuantities() {
        AllocationResult result = service.allocate(item(2), 5);
        assertTrue(result.message().contains("要求 5"));
        assertTrue(result.message().contains("在庫 2"));
    }

    @Test
    @DisplayName("引当数量 0 は IllegalArgumentException")
    void allocateRejectsZeroQuantity() {
        assertThrows(IllegalArgumentException.class, () -> service.allocate(item(10), 0));
    }

    @Test
    @DisplayName("引当数量が負の値は IllegalArgumentException")
    void allocateRejectsNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> service.allocate(item(10), -1));
    }
}
