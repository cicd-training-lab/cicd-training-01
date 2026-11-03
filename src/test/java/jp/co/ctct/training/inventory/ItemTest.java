package jp.co.ctct.training.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("商品 (Item) のバリデーション")
class ItemTest {

    @Test
    @DisplayName("正しい値で生成できる")
    void createsValidItem() {
        Item item = new Item("ITEM-003", "ノート PC", 10, 3);
        assertEquals(10, item.stock());
        assertEquals(3, item.reorderPoint());
    }

    @Test
    @DisplayName("商品 ID が空文字なら IllegalArgumentException")
    void rejectsBlankId() {
        assertThrows(IllegalArgumentException.class, () -> new Item(" ", "ノート PC", 10, 3));
    }

    @Test
    @DisplayName("在庫数が負の値なら IllegalArgumentException")
    void rejectsNegativeStock() {
        assertThrows(IllegalArgumentException.class, () -> new Item("ITEM-003", "ノート PC", -1, 3));
    }

    @Test
    @DisplayName("withStock は在庫数だけを変更した新しい Item を返す")
    void withStockReturnsUpdatedCopy() {
        Item original = new Item("ITEM-003", "ノート PC", 10, 3);
        Item updated = original.withStock(7);
        assertEquals(7, updated.stock());
        assertEquals(10, original.stock());
        assertEquals(original.id(), updated.id());
    }
}
