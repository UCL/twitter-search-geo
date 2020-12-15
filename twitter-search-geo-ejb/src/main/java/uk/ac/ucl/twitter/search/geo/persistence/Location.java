package uk.ac.ucl.twitter.search.geo.persistence;

/**
 * List of locations and their coordinates where tweets are collected from.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public enum Location {

  /**
   * Geo coordinates for Greater London.
   */
  Greater_London(51.507300, -0.127700, 15),

  /**
   * Geo coordinates for Greater Manchester.
   */
  Greater_Manchester(53.500000, -2.300000, 15),

  /**
   * Geo coordinates for the centre point of the West Midlands.
   */
  West_Midlands(52.511794, -1.975307, 10),

  /**
   * Geo coordinates for the centre point of West Yorkshire.
   */
  West_Yorkshire(53.716667, -1.633333, 15),

  /**
   * Geo coordinates for Liverpool.
   */
  Liverpool(53.409000, -2.934000, 10),

  /**
   * Geo coordinates for the centre point of South Hampshire.
   */
  South_Hampshire(50.866667, -1.266667, 15),

  /**
   * Geo coordinates for the centre point of the Tyneside region.
   */
  Tyneside(54.987500, -1.458333, 15),

  /**
   * Geo coordinates for Nottingham.
   */
  Nottingham(52.960000, -1.150000, 10),

  /**
   * Geo coordinates for Sheffield.
   */
  Sheffield(53.395000, -1.455000, 10),

  /**
   * Geo coordinates for Bristol.
   */
  Bristol(51.466667, -2.583333, 10),

  /**
   * Geo coordinates for Leicester.
   */
  Leicester(52.630000, -1.130000, 10),

  /**
   * Geo coordinates for the centre point of Brighton and Hove area.
   */
  Brighton_and_Hove(50.827778, -0.152778, 10),

  /**
   * Geo coordinates for the centre point of Bournemouth Poole area.
   */
  Bournemouth_Poole(50.720790, -1.870330, 10),

  /**
   * Geo coordinates for the centre point of Teesside area.
   */
  Teesside(54.591667, -1.187500, 15),

  /**
   * Geo coordinates for Stoke on Trent.
   */
  Stoke_on_Trent(53, -2.183333, 10),

  /**
   * Geo coordinates for Coventry.
   */
  Coventry(52.430000, -1.500000, 10),

  /**
   * Geo coordinates for the centre point of Sunderland Wearside area.
   */
  Sunderland_Wearside(54.904000, -1.381000, 10),

  /**
   * Geo coordinates for Birkenhead.
   */
  Birkenhead(53.393000, -3.014000, 10),

  /**
   * Geo coordinates for Reading.
   */
  Reading(51.454167, -0.973056, 10),

  /**
   * Geo coordinates for Kingston-upon-Hull.
   */
  Kingston_upon_Hull(53.744333, -0.332500, 10),

  /**
   * Geo coordinates for the centre point of Preston Central Lancashire area.
   */
  Preston_Central_Lancashire(53.710000, -2.643000, 10),

  /**
   * Geo coordinates for Southend-on-Sea.
   */
  Southend_on_Sea(51.544300, 0.670000, 10),

  /**
   * Geo coordinates for Derby.
   */
  Derby(52.921944, -1.475833, 10),

  /**
   * Geo coordinates for Plymouth.
   */
  Plymouth(50.371389, -4.142222, 10),

  /**
   * Geo coordinates for Luton.
   */
  Luton(51.899000, -0.479000, 10),

  /**
   * Geo coordinates for Farnborough Aldershot.
   */
  Farnborough_Aldershot(51.240000, -0.750000, 10),

  /**
   * Geo coordinates for Medway Towns.
   */
  Medway_Towns(51.390000, 0.540000, 10),

  /**
   * Geo coordinates for Blackpool.
   */
  Blackpool(53.814167, -3.050278, 10),

  /**
   * Geo coordinates for Milton Keynes.
   */
  Milton_Keynes(52.074760, -0.734500, 10),

  /**
   * Geo coordinates for Barnsley Dearne Valley.
   */
  Barnsley_Dearne_Valley(53.5537, -1.4791, 10),

  /**
   * Geo coordinates for Northampton.
   */
  Northampton(52.237211, -0.896028, 10),

  /**
   * Geo coordinates for Norwich.
   */
  Norwich(52.628333, 1.296667, 10),

  /**
   * Geo coordinates for Swindon.
   */
  Swindon(51.560000, -1.780000, 10),

  /**
   * Geo coordinates for Crawley.
   */
  Crawley(51.109167, -0.187222, 10),

  /**
   * Geo coordinates for Ipswich.
   */
  Ipswich(52.059444, 1.155556, 10),

  /**
   * Geo coordinates for Wigan.
   */
  Wigan(53.544000, -2.631000, 10),

  /**
   * Geo coordinates for Mansfield.
   */
  Mansfield(53.140000, -1.200000, 10),

  /**
   * Geo coordinates for Oxford.
   */
  Oxford(51.751944, -1.257778, 10),

  /**
   * Geo coordinates for Warrington.
   */
  Warrington(53.387320, -2.602880, 10),

  /**
   * Geo coordinates for Slough.
   */
  Slough(51.510000, -0.59000, 100),

  /**
   * Geo coordinates for Peterborough.
   */
  Peterborough(52.583333, -0.250000, 10),

  /**
   * Geo coordinates for Cambridge.
   */
  Cambridge(52.205000, 0.119000, 10),

  /**
   * Geo coordinates for Cambridge.
   */
  Doncaster(53.516000, -1.133000, 10),

  /**
   * Geo coordinates for York.
   */
  York(53.958333, -1.080278, 10),

  /**
   * Geo coordinates for the centre point of Gloucester area.
   */
  Gloucester(51.870000, -2.240000, 10),

  /**
   * Geo coordinates for Burnley.
   */
  Burnley(53.789000, -2.248000, 10),

  /**
   * Geo coordinates for Telford.
   */
  Telford(52.676600, -2.446900, 10),

  /**
   * Geo coordinates for Blackburn.
   */
  Blackburn(53.744900, -2.476900, 10),

  /**
   * Geo coordinates for Basildon.
   */
  Basildon(51.576100, 0.488600, 10),

  /**
   * Geo coordinates for Grimsby.
   */
  Grimsby(53.559500, -0.068000, 10),

  /**
   * Geo coordinates for Hastings.
   */
  Hastings(50.852990, 0.564590, 10),

  /**
   * Geo coordinates for High Wycombe.
   */
  High_Wycombe(51.628250, -0.748840, 10),

  /**
   * Geo coordinates for the Thanet area.
   */
  Thanet(51.366667, 1.383333, 10),

  /**
   * Geo coordinates for Accrington Rossendale.
   */
  Accrington_Rossendale(53.683333, -2.250000, 10),

  /**
   * Geo coordinates for Burton upon Trent.
   */
  Burton_upon_Trent(52.799500, -1.638000, 10),

  /**
   * Geo coordinates for Colchester.
   */
  Colchester(51.891700, 0.903000, 10),

  /**
   * Geo coordinates for Eastbourne.
   */
  Eastbourne(50.770000, 0.280000, 10),

  /**
   * Geo coordinates for Exeter.
   */
  Exeter(50.716667, -3.533333, 10),

  /**
   * Geo coordinates for Cheltenham.
   */
  Cheltenham(51.883333, -2.066667, 10),

  /**
   * Geo coordinates for Paignton Torquay.
   */
  Paignton_Torquay(50.479200, -3.530500, 10),

  /**
   * Geo coordinates for Lincoln.
   */
  Lincoln(53.232700, -0.537600, 10),

  /**
   * Geo coordinates for Chesterfield.
   */
  Chesterfield(53.235833, -1.427500, 10),

  /**
   * Geo coordinates for Chelmsford.
   */
  Chelmsford(51.736100, 0.479800, 10),

  /**
   * Geo coordinates for Basingstoke.
   */
  Basingstoke(51.266700, -1.087600, 10),

  /**
   * Geo coordinates for Maidstone.
   */
  Maidstone(51.272000, 0.529000, 10),

  /**
   * Geo coordinates for Bedford.
   */
  Bedford(52.133700, -0.457700, 10),

  /**
   * Geo coordinates for Worcester.
   */
  Worcester(52.191230, -2.222310, 10),

  /**
   * Geo coordinates for Cardiff.
   */
  Cardiff(51.470000, -3.173000, 10),

  /**
   * Geo coordinates for Newport.
   */
  Newport(51.583333, -3.000000, 10),

  /**
   * Geo coordinates for Swansea.
   */
  Swansea(51.616667, -3.95, 10),

  /**
   * Geo coordinates for Greater Glasgow.
   */
  Greater_Glasgow(55.862000, -4.254480, 10),

  /**
   * Geo coordinates for Edinburgh.
   */
  Edinburgh(55.953056, -3.188889, 10),

  /**
   * Geo coordinates for Aberdeen.
   */
  Aberdeen(57.152600, -2.110000, 10),

  /**
   * Geo coordinates for Dundee.
   */
  Dundee(56.464000, -2.970000, 10),

  /**
   * Geo coordinates for Falkirk.
   */
  Falkirk(56.001100, -3.783500, 10),

  /**
   * Geo coordinates for East Kilbride.
   */
  East_Kilbride(55.764529, -4.177115, 10),

  /**
   * Geo coordinates for Greenock.
   */
  Greenock(55.949570, -4.764150, 10),

  /**
   * Geo coordinates for Blantyre Hamilton.
   */
  Blantyre_Hamilton(55.777420, -4.055050, 10),

  /**
   * Geo coordinates for Ayr Prestwick.
   */
  Ayr_Prestwick(55.458000, -4.629000, 10),

  /**
   * Geo coordinates for Livingston.
   */
  Livingston(55.883400, -3.515700, 10),

  /**
   * Geo coordinates for the Belfast Metropolitan area.
   */
  Belfast_Metropolitan(54.597000, -5.930000, 10),

  /**
   * Geo coordinates for Derry.
   */
  Derry(54.994000, -7.326000, 10),

  /**
   * Geo coordinates for Craigavon.
   */
  Craigavon(54.447222, -6.388333, 10);

  /**
   * The geo latitude in degrees.
   */
  private final double latitude;

  /**
   * The geo longitude in degrees.
   */
  private final double longitude;

  /**
   * The radius in miles for the geo area.
   */
  private final int radius;

  Location(final double lat, final double lon, final int rds) {
    latitude = lat;
    longitude = lon;
    radius = rds;
  }

  /**
   * Getter for the geographical latitude of a location.
   * @return The latitude coordinate
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Getter for the geographical longitude of a location.
   * @return The longitude coordinate
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Getter for the radius.
   * @return The radius
   */
  public String getRadius() {
    return radius + "km";
  }

}
