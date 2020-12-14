package uk.ac.ucl.twitter.search.geo.persistence;

/**
 * List of locations and their coordinates where tweets are collected from.
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public enum Location {

  Greater_London(51.507300, -0.127700, 15),
  Greater_Manchester(53.500000, -2.300000, 15),
  West_Midlands(52.511794, -1.975307, 10),
  West_Yorkshire(53.716667, -1.633333, 15),
  Liverpool(53.409000, -2.934000, 10),
  South_Hampshire(50.866667, -1.266667, 15),
  Tyneside(54.987500, -1.458333, 15),
  Nottingham(52.960000, -1.150000, 10),
  Sheffield(53.395000, -1.455000, 10),
  Bristol(51.466667, -2.583333, 10),
  Leicester(52.630000, -1.130000, 10),
  Brighton_and_Hove(50.827778, -0.152778, 10),
  Bournemouth_Poole(50.720790, -1.870330, 10),
  Teesside(54.591667, -1.187500, 15),
  Stoke_on_Trent(53, -2.183333, 10),
  Coventry(52.430000, -1.500000, 10),
  Sunderland_Wearside(54.904000, -1.381000, 10),
  Birkenhead(53.393000, -3.014000, 10),
  Reading(51.454167, -0.973056, 10),
  Kingston_upon_Hull(53.744333, -0.332500, 10),
  Preston_Central_Lancashire(53.710000, -2.643000, 10),
  Southend_on_Sea(51.544300, 0.670000, 10),
  Derby(52.921944, -1.475833, 10),
  Plymouth(50.371389, -4.142222, 10),
  Luton(51.899000, -0.479000, 10),
  Farnborough_Aldershot(51.240000, -0.750000, 10),
  Medway_Towns(51.390000, 0.540000, 10),
  Blackpool(53.814167, -3.050278, 10),
  Milton_Keynes(52.074760, -0.734500, 10),
  Barnsley_Dearne_Valley(53.5537, -1.4791, 10),
  Northampton(52.237211, -0.896028, 10),
  Norwich(52.628333, 1.296667, 10),
  Swindon(51.560000, -1.780000, 10),
  Crawley(51.109167, -0.187222, 10),
  Ipswich(52.059444, 1.155556, 10),
  Wigan(53.544000, -2.631000, 10),
  Mansfield(53.140000, -1.200000, 10),
  Oxford(51.751944, -1.257778, 10),
  Warrington(53.387320, -2.602880, 10),
  Slough(51.510000, -0.59000, 100),
  Peterborough(52.583333, -0.250000, 10),
  Cambridge(52.205000, 0.119000, 10),
  Doncaster(53.516000, -1.133000, 10),
  York(53.958333, -1.080278, 10),
  Gloucester(51.870000, -2.240000, 10),
  Burnley(53.789000, -2.248000, 10),
  Telford(52.676600, -2.446900, 10),
  Blackburn(53.744900, -2.476900, 10),
  Basildon(51.576100, 0.488600, 10),
  Grimsby(53.559500, -0.068000, 10),
  Hastings(50.852990, 0.564590, 10),
  High_Wycombe(51.628250, -0.748840, 10),
  Thanet(51.366667, 1.383333, 10),
  Accrington_Rossendale(53.683333, -2.250000, 10),
  Burton_upon_Trent(52.799500, -1.638000, 10),
  Colchester(51.891700, 0.903000, 10),
  Eastbourne(50.770000, 0.280000, 10),
  Exeter(50.716667, -3.533333, 10),
  Cheltenham(51.883333, -2.066667, 10),
  Paignton_Torquay(50.479200, -3.530500, 10),
  Lincoln(53.232700, -0.537600, 10),
  Chesterfield(53.235833, -1.427500, 10),
  Chelmsford(51.736100, 0.479800, 10),
  Basingstoke(51.266700, -1.087600, 10),
  Maidstone(51.272000, 0.529000, 10),
  Bedford(52.133700, -0.457700, 10),
  Worcester(52.191230, -2.222310, 10),
  Cardiff(51.470000, -3.173000, 10),
  Newport(51.583333, -3.000000, 10),
  Swansea(51.616667, -3.95, 10),
  Greater_Glasgow(55.862000, -4.254480, 10),
  Edinburgh(55.953056, -3.188889, 10),
  Aberdeen(57.152600, -2.110000, 10),
  Dundee(56.464000, -2.970000, 10),
  Falkirk(56.001100, -3.783500, 10),
  East_Kilbride(55.764529, -4.177115, 10),
  Greenock(55.949570, -4.764150, 10),
  Blantyre_Hamilton(55.777420, -4.055050, 10),
  Ayr_Prestwick(55.458000, -4.629000, 10),
  Livingston(55.883400, -3.515700, 10),
  Belfast_Metropolitan(54.597000, -5.930000, 10),
  Derry(54.994000, -7.326000, 10),
  Craigavon(54.447222, -6.388333, 10);

  private final double latitude;
  private final double longitude;
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
  public String getRadius() { return radius + "km"; }
}
