package ua.pp.shurgent.tfctech.core;

import static com.bioxx.tfc.WorldGen.Generators.WorldGenOre.oreList;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import ua.pp.shurgent.tfctech.TFCTech;

import com.bioxx.tfc.Core.Config.TFC_ConfigFiles;
import com.bioxx.tfc.WorldGen.Generators.OreSpawnData;
import com.bioxx.tfc.api.Constant.Global;
import com.google.common.collect.ObjectArrays;

public class ModOptions {
	// Category names
	public static final String GENERAL = "general";
	public static final String MATERIALS = "materials";
	public static final String CRAFTING = "crafting";
	public static final String WORLDGEN = "world";
	public static final String FURNACE = "furnace";

	// === GENERAL
	public static boolean cfgCheckUpdates;
	public static boolean cfgEnableNEIHide;
	
	// === MATERIALS
	public static int cfgDustMetalAmount;
	public static int cfgNuggetMetalAmount;
	public static int cfgPlateMetalAmount;
	public static int cfgPlate2xMetalAmount;
	
	// === FURNACE
	public static boolean cfgEnableFurnaceClayFiring;
	public static boolean cfgEnableFurnaceRubberFiring;
	public static int cfgFuelValueHeveaLog;
	public static int cfgFuelValueRubber;

	// === CRAFTING
	public static int cfgNuggetsFromIngot;

	public static double cfgGlueBoilingSpeed;
	public static int cfgGlueQtyFromBucket;
	public static boolean cfgFluidPipesNeedGlue;
	
	// === WORLD GEN
	public static boolean cfgDropQuartz;
	public static int cfgDropQuartzIgExChance;
	public static int cfgDropQuartzIgInChance;
	public static int cfgDropQuartzMMChance;
	public static int cfgDropQuartzSedChance;
	public static int cfgDropQuartzMinQty;
	public static int cfgDropQuartzMaxQty;
	
	public static boolean cfgHeveaDamage;
	public static int cfgHeveaMaxDamage;
	
	// TFC ore config
	private static Configuration oresConfig;

	private static final String[] ALLOWED_TYPES = new String[] { "default", "veins" };
	private static final String[] ALLOWED_SIZES = new String[] { "small", "medium", "large" };
	private static final String[] ALLOWED_BASE_ROCKS = ObjectArrays.concat(Global.STONE_ALL, new String[] { "igneous intrusive", "igneous extrusive", "sedimentary", "metamorphic" }, String.class);

	public static void loadSettings() {
		TFCTech.LOG.info("Loading options.");

		Configuration config;

		try {
			config = new Configuration(new File(ua.pp.shurgent.tfctech.TFCTech.instance.getMinecraftDirectory(), ModDetails.ConfigFilePath
					+ ModDetails.ConfigFileName));
			config.load();
		} catch (Exception ex) {
			TFCTech.LOG.error("Error while trying to access settings configuration!");
			config = null;
		}

		/** Start Here **/
		
		// === General
		cfgEnableNEIHide = getBooleanFor(config, GENERAL, "EnableNEIHide", true, "Set to false to show hidden items in NEI.");
		cfgCheckUpdates = getBooleanFor(config, GENERAL, "CheckUpdates", true, "Check for mod updates.");
		
		// === Materials
		cfgDustMetalAmount = getIntFor(config, MATERIALS, "DustUnits", 100, 1, 3000, "The metal units provided by a single dust.");
		cfgNuggetMetalAmount = getIntFor(config, MATERIALS, "NuggetUnits", 20, 1, 3000, "The metal units provided by a single nugget.");
		cfgPlateMetalAmount = getIntFor(config, MATERIALS, "PlateUnits", 100, 1, 3000, "The metal units provided by a single plate.");
		cfgPlate2xMetalAmount = getIntFor(config, MATERIALS, "Plate2xUnits", 200, 1, 3000, "The metal units provided by a single dense plate.");
		
		// === Crafting
		cfgNuggetsFromIngot = getIntFor(config, CRAFTING, "NuggetsFromIngot", 5, 2, 100, "Number of nuggets obtained from one ingot.");
		
		cfgGlueBoilingSpeed = getDoubleFor(config, CRAFTING, "GlueBoilingSpeed", 0.05, "How fast the glue be prepared. Bigger value - faster preparation.");
		cfgGlueQtyFromBucket = getIntFor(config, CRAFTING, "GlueQtyFromBucket", 8, 1, 64, "Number of glue balls obtained from one bucket of glue.");
		cfgFluidPipesNeedGlue = getBooleanFor(config, CRAFTING, "FluidPipesNeedGlue", true, "If true, then the crafting of fluid pipes will need glue.");
		
		// === World Generation
		cfgDropQuartz = getBooleanFor(config, WORLDGEN, "QuartzDropEnable", true, "Set to false to disable Quartz drops from stone.");
		cfgDropQuartzMinQty = getIntFor(config, WORLDGEN, "QuartzDropMinQty", 1, 1, 10, "Quartz drop minimum quantity.");
		cfgDropQuartzMaxQty = getIntFor(config, WORLDGEN, "QuartzDropMaxQty", 3, 1, 10, "Quartz drop maximum quantity.");
		cfgDropQuartzIgExChance = getIntFor(config, WORLDGEN, "QuartzIgExDropChance", 250, 0, 1000, "Quartz drop chance (1 in X) when harvesting Igneous Extrusive stone.");
		cfgDropQuartzIgInChance = getIntFor(config, WORLDGEN, "QuartzIgInDropChance", 150, 0, 1000, "Quartz drop chance (1 in X) when harvesting Igneous Intrusive stone.");
		cfgDropQuartzMMChance = getIntFor(config, WORLDGEN, "QuartzMMDropChance", 100, 0, 1000, "Quartz drop chance (1 in X) when harvesting Metamorphic stone.");
		cfgDropQuartzSedChance = getIntFor(config, WORLDGEN, "QuartzSedDropChance", 50, 0, 1000, "Quartz drop chance (1 in X) when harvesting Sedimentary stone.");
		
		cfgHeveaDamage = getBooleanFor(config, WORLDGEN, "HeveaDamageEnable", true, "Set to false to disable Hevea tree damage from tapping.");
		cfgHeveaMaxDamage = getIntFor(config, WORLDGEN, "HeveaMaxDamage", 15, 1, 15, "Number of Hevea trunk scratches before the tree gets damaged.");
		
		// === Furnace
		cfgEnableFurnaceClayFiring = getBooleanFor(config, FURNACE, "FurnaceClayFiringEnable", true, "Set to false to disable furnace clay firing recipes.");
		cfgEnableFurnaceRubberFiring = getBooleanFor(config, FURNACE, "FurnaceRubberFiringEnable", true, "Set to false to disable furnace rubber firing recipe.");
		cfgFuelValueHeveaLog = getIntFor(config, FURNACE, "FuelValueHeveaLog", 800, 1, 20000, "Burn time for Hevea Log.");
		cfgFuelValueRubber = getIntFor(config, FURNACE, "FuelValueRubber", 200, 1, 20000, "Burn time for Rubber.");

		/** End Here */
		if (config != null)
			config.save();

		TFCTech.LOG.info("Done loading options.");
	}

	public static boolean getBooleanFor(Configuration config, String heading, String item, boolean value) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			return prop.getBoolean(value);
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add Integer, config wasn't loaded properly!");
		}
		return value;
	}

	public static boolean getBooleanFor(Configuration config, String heading, String item, boolean value, String comment) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return prop.getBoolean(value);
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add Integer, config wasn't loaded properly!");
		}
		return value;
	}

	public static int getIntFor(Configuration config, String heading, String item, int value) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			return prop.getInt(value);
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add Integer, config wasn't loaded properly!");
		}
		return value;
	}

	public static int getIntFor(Configuration config, String heading, String item, int value, int minValue, int maxValue, String comment) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			prop.comment = comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + value + "]";
			prop.setMinValue(minValue);
			prop.setMaxValue(maxValue);
			if (prop.getInt(value) < minValue || prop.getInt(value) > maxValue) {
				TFCTech.LOG.info("An invalid value has been entered for " + item
						+ " in the config file. Reverting to the default value.");
				prop.set(value);
				return value;
			}
			return prop.getInt(value);
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add Integer, config wasn't loaded properly!");
		}
		return value;
	}

	public static double getDoubleFor(Configuration config, String heading, String item, double value) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			return prop.getDouble(value);
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add Double, config wasn't loaded properly!");
		}
		return value;
	}

	public static double getDoubleFor(Configuration config, String heading, String item, double value, String comment) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return prop.getDouble(value);
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add Double, config wasn't loaded properly!");
		}
		return value;
	}

	public static String getStringFor(Configuration config, String heading, String item, String value) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			return prop.getString();
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add String, config wasn't loaded properly!");
		}
		return value;
	}

	public static String getStringFor(Configuration config, String heading, String item, String value, String comment) {
		if (config == null)
			return value;
		try {
			Property prop = config.get(heading, item, value);
			prop.comment = comment;
			return prop.getString();
		} catch (Exception e) {
			TFCTech.LOG.error("Error while trying to add String, config wasn't loaded properly!");
		}
		return value;
	}

	public static void reloadOres() {
		oreList.put("Bauxite", getOreData("Bauxite", "default", "normal", ModDetails.ModID + ":Ore4", 0, 90, new String[] { "sedimentary" }, 5, 128, 80, 60));
		oreList.put(
				"Bauxite Surface",
				getOreData("Bauxite Surface", "veins", "small", ModDetails.ModID + ":Ore4", 0, 35, new String[] { "igneous extrusive", "sedimentary" }, 128, 240, 40, 40));

		// getCategoryNames returns an ImmutableSet
		for (String s : oresConfig.getCategoryNames()) {
			// If this is a new entry, otherwise it has already been added by the previous bit of code.
			if (!oreList.containsKey(s))
				oreList.put(s, getOreData(s, "default", "small", "Ore", 0, 100, new String[] { "granite", "basalt", "sedimentary" }, 5, 128, 50, 50));
		}

		if (oresConfig.hasChanged())
			oresConfig.save();
	}

	private static OreSpawnData getOreData(String category, String type, String size, String blockName, int meta, int rarity, String[] rocks, int min, int max,
			int v, int h) {
		oresConfig = TFC_ConfigFiles.getOresConfig();
		return new OreSpawnData(
			oresConfig.get(category, "type", type).setValidValues(ALLOWED_TYPES).getString(),
			oresConfig.get(category, "size", size).setValidValues(ALLOWED_SIZES).getString(),
			oresConfig.get(category, "oreName", blockName).getString(),
			oresConfig.get(category, "oreMeta",	meta).getInt(),
			oresConfig.get(category, "rarity", rarity).getInt(),
			oresConfig.get(category, "baseRocks", rocks).setValidValues(ALLOWED_BASE_ROCKS).getStringList(),
			oresConfig.get(category, "Minimum Height", min).getInt(),
			oresConfig.get(category, "Maximum Height", max).getInt(),
			oresConfig.get(category, "Vertical Density", v).getInt(),
			oresConfig.get(category, "Horizontal Density", h).getInt()
		);
	}
}