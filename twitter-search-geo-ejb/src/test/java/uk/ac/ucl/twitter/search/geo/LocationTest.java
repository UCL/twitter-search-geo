package uk.ac.ucl.twitter.search.geo;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

  @Test
  public void testAllLocationsExist() {
    final String[] expected = new String[]{
      "Aberdeen",
      "Accrington_Rossendale",
      "Ayr_Prestwick",
      "Barnsley_Dearne_Valley",
      "Basildon",
      "Basingstoke",
      "Bedford",
      "Belfast_Metropolitan",
      "Birkenhead",
      "Blackburn",
      "Blackpool",
      "Blantyre_Hamilton",
      "Bournemouth_Poole",
      "Brighton_and_Hove",
      "Bristol",
      "Burnley",
      "Burton_upon_Trent",
      "Cambridge",
      "Cardiff",
      "Chelmsford",
      "Cheltenham",
      "Chesterfield",
      "Colchester",
      "Coventry",
      "Craigavon",
      "Crawley",
      "Derby",
      "Derry",
      "Doncaster",
      "Dundee",
      "East_Kilbride",
      "Eastbourne",
      "Edinburgh",
      "Exeter",
      "Falkirk",
      "Farnborough_Aldershot",
      "Gloucester",
      "Greater_Glasgow",
      "Greater_London",
      "Greater_Manchester",
      "Greenock",
      "Grimsby",
      "Hastings",
      "High_Wycombe",
      "Ipswich",
      "Kingston_upon_Hull",
      "Leicester",
      "Lincoln",
      "Liverpool",
      "Livingston",
      "Luton",
      "Maidstone",
      "Mansfield",
      "Medway_Towns",
      "Milton_Keynes",
      "Newport",
      "Northampton",
      "Norwich",
      "Nottingham",
      "Oxford",
      "Paignton_Torquay",
      "Peterborough",
      "Plymouth",
      "Preston_Central_Lancashire",
      "Reading",
      "Sheffield",
      "Slough",
      "South_Hampshire",
      "Southend_on_Sea",
      "Stoke_on_Trent",
      "Sunderland_Wearside",
      "Swansea",
      "Swindon",
      "Teesside",
      "Telford",
      "Thanet",
      "Tyneside",
      "Warrington",
      "West_Midlands",
      "West_Yorkshire",
      "Wigan",
      "Worcester",
      "York"
    };
    String[] result = Stream.of(Location.values())
      .map(Location::name)
      .sorted()
      .toArray(String[]::new);
    assertArrayEquals(expected, result);
  }

  @Test
  public void testGetLatitude() throws NoSuchMethodException {
    Method method = Location.class.getDeclaredMethod("getLatitude");
    Class<?> returnType = method.getReturnType();
    assertEquals(returnType.getName(), "double");
  }

  @Test
  public void testGetLongitude() throws NoSuchMethodException {
    Method method = Location.class.getDeclaredMethod("getLongitude");
    Class<?> returnType = method.getReturnType();
    assertEquals(returnType.getName(), "double");
  }

}
