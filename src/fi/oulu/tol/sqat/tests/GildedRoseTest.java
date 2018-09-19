package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	// Example scenarios for testing
	// Item("+5 Dexterity Vest", 10, 20));
	// Item("Aged Brie", 2, 0));
	// Item("Elixir of the Mongoose", 5, 7));
	// Item("Sulfuras, Hand of Ragnaros", 0, 80));
	// Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
	// Item("Conjured Mana Cake", 3, 6));

	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(11, itemBrie.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_NotAgedBrie_Quality_10_9() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Definitely Not Aged Brie", 2, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(9, itemBrie.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_SecondItem_SellIn_4_3() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 3, 10));
		store.addItem(new Item("Broom", 4, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		// Item itemBrie = items.get(0);
		Item broom = items.get(1);
		assertEquals(3, broom.getSellIn());
	}

	@Test
	public void testUpdateEndOfDay_SecondItem_Quality_10_9() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 3, 10));
		store.addItem(new Item("Broom", 4, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item broom = items.get(1);
		assertEquals(9, broom.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_BothItemQuality_Change() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 3, 10));
		store.addItem(new Item("Broom", 4, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		Item broom = items.get(1);
		assertEquals(13, itemBrie.getQuality());
		assertEquals(9, broom.getQuality());
	}
	@Test
	public void testUpdateEndOfDay_BothItemSellIn_Change() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 3, 10));
		store.addItem(new Item("Broom", 4, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		Item broom = items.get(1);
		assertEquals(2, itemBrie.getSellIn());
		assertEquals(3, broom.getSellIn());
	}
	@Test
	public void testQualityDegradation_After_SellDate_10_7() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Broom", 1, 10));

		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item broom = items.get(0);
		assertEquals(7, broom.getQuality());
	}

	@Test
	public void testNegativeQualityValues() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Broom", 1, 0));

		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item broom = items.get(0);
		assertEquals(0, broom.getQuality());
	}

	@Test
	public void testNegativeQualityValues_Create() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Broom", 1, -10));
		fail("Items can NOT have negative quality");
	}

	@Test
	public void testQualityMoreThan50() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 10, 49));

		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item broom = items.get(0);
		assertEquals(50, broom.getQuality());
	}

	@Test
	public void testQualityMoreThan50_Create() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Broom", 10, 149));
		fail("Items cannot have quality more than 50");
	}

	@Test
	public void testNegativeSellIn_Create() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Broom", -1, 20));
		fail("Items cannot have sellIn lower than 0");
	}

	@Test
	public void testSulfuras_SellIn_10_10() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 10, 80));

		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item sulfuras = items.get(0);
		assertEquals(10, sulfuras.getSellIn());
	}

	@Test
	public void testSulfuras_Quality_80_80() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));

		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item sulfuras = items.get(0);
		assertEquals(80, sulfuras.getQuality());
	}

	@Test
	public void testBackStagePassesQuality_Less_Than_11Days_10_12() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item brie = items.get(0);
		assertEquals(12, brie.getQuality());
	}

	@Test
	public void testBackStagePassesQuality_Less_Than_6Days_10_13() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item brie = items.get(0);
		assertEquals(13, brie.getQuality());
	}

	@Test
	public void testBackStagePassesQuality_SellIn_Last_Day_10_13() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item brie = items.get(0);
		assertEquals(13, brie.getQuality());
	}

	@Test
	public void testBackStagePassesQuality_SellIn_0Days_10_0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item brie = items.get(0);
		assertEquals(0, brie.getQuality());
	}

	@Test
	public void testUpdateEndOfDay() {
		fail("Test not implemented");
	}
}
