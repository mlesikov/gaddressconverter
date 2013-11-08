package com.mlesikov.addressconverter;


import com.google.common.collect.Lists;
import com.mlesikov.addressconverter.pont.Point;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

/**
 * @author Mihail Lesikov (mlesikov@gmail.com)
 */
public class AddressConverterTest {


  @Test
  public void singleAddress() throws Exception {

    GoogleResponse res = new AddressConverter().convertToLatLong("България, Стара Загора, Гурко 24");

    String lonlat = "";
    if (res.getStatus().equals("OK")) {
      for (Result result : res.getResults()) {
        System.out.println("Lattitude of address is :" + result.getGeometry().getLocation().getLat());
        System.out.println("Longitude of address is :" + result.getGeometry().getLocation().getLng());
        lonlat = result.getGeometry().getLocation().getLat() + "," + result.getGeometry().getLocation().getLng();
        System.out.println("LlatlongString : " + lonlat);
        System.out.println("Location is " + result.getGeometry().getLocation_type());
      }
    } else {
      System.out.println(res.getStatus());
    }

    System.out.println("\n");
    GoogleResponse res1 = new AddressConverter().convertFromLatLong(lonlat);
    if (res1.getStatus().equals("OK")) {
      for (Result result : res1.getResults()) {
        System.out.println("address is :" + result.getFormatted_address());
      }
    } else {
      System.out.println(res1.getStatus());
    }
  }

  //  1. Стара Загора, ул.Поп Минчо Кънчев 84 ет.3 ап.7  | 23.2342344234 | 23.342342342 | in coverage / out of in coverage
  @Test
  public void checkAddressesFromFile() throws Exception {
    Scanner scanner = new Scanner(this.getClass().getResourceAsStream("test.txt"));
//    FileWriter fstream = new FileWriter("sz addresses_0_8000.txt");
//    BufferedWriter out = new BufferedWriter(fstream);


    while (scanner.hasNext()) {

      String address = scanner.nextLine();


      String escapedAddress = address.replaceAll("бул.", "").replaceAll("ул.", "").replaceAll("кв.", "ж.к. ");
      if (escapedAddress.contains("ет.")) {
        escapedAddress = escapedAddress.substring(0, escapedAddress.indexOf("ет."));
      }
      if (escapedAddress.contains("вх.")) {
        escapedAddress = escapedAddress.substring(0, escapedAddress.indexOf("вх."));
      }
      if (escapedAddress.contains("ап.")) {
        escapedAddress = escapedAddress.substring(0, escapedAddress.indexOf("ап."));
      }
      if (escapedAddress.contains("/")) {
        escapedAddress = escapedAddress.substring(0, escapedAddress.indexOf("/"));
      }

      String searchAddress = "България, " + escapedAddress.substring(3, escapedAddress.length());
//      System.out.println(searchAddress);

      GoogleResponse res = new GoogleResponse();

      try {
        res = new AddressConverter().convertToLatLong(searchAddress);
      } catch (Exception e) {
        System.out.println("error " + e.getMessage());
      }


      Thread.sleep(50);

      String lonlat = "";

      if (res.getStatus().equals("OK")) {
        for (Result result : res.getResults()) {

          lonlat = result.getGeometry().getLocation().getLat() + " | " + result.getGeometry().getLocation().getLng();

          String row = address + " | " + lonlat;

          if (new Point(Double.parseDouble(result.getGeometry().getLocation().getLat()), Double.parseDouble(result.getGeometry().getLocation().getLng())).isInPolygon(staraZagoraCoverage)) {
            row = row + "| in coverage";
          } else {
            row = row + "| out of coverage";
          }

          GoogleResponse res1 = new AddressConverter().convertFromLatLong(result.getGeometry().getLocation().getLat() + " , " + result.getGeometry().getLocation().getLng());

          if (res1.getStatus().equals("OK") && res1.getResults().length > 0) {
            System.out.println(row + "| google says : " + res1.getResults()[0].getFormatted_address());
            Thread.sleep(50);
          } else {
//                System.out.println(res1.getStatus());
          }


//          System.out.println(row + " | търсим " + searchAddress);
//          out.write(row + "\n");

        }
      } else {
        System.out.println(address + " |  " + res.getStatus());
      }

    }
//     out.cloase();

  }


  List<Point> staraZagoraCoverage = Lists.newArrayList(
          new Point(42.413128243108176, 25.597355291247368),
          new Point(42.413413402363545, 25.59701196849346),
          new Point(42.41354013939421, 25.59478037059307),
          new Point(42.414649077488, 25.59327833354473),
          new Point(42.41547284737866, 25.59383623301983),
          new Point(42.416359972082866, 25.595724508166313),
          new Point(42.41683521229794, 25.59679739177227),
          new Point(42.41756390696607, 25.598471090197563),
          new Point(42.41759558915088, 25.60001604259014),
          new Point(42.41743717806684, 25.60130350291729),
          new Point(42.4177856819235, 25.602590963244438),
          new Point(42.41940144722477, 25.601861402392387),
          new Point(42.4204469202341, 25.601389333605766),
          new Point(42.42228437600806, 25.599887296557426),
          new Point(42.424787030870874, 25.597484037280083),
          new Point(42.42586409208735, 25.596969053149223),
          new Point(42.42703616709152, 25.59804193675518),
          new Point(42.429063488336, 25.59808485209942),
          new Point(42.42991874482091, 25.59778444468975),
          new Point(42.43036220655231, 25.5985140055418),
          new Point(42.42979204089282, 25.599200651049614),
          new Point(42.42868337059697, 25.599930211901665),
          new Point(42.42744797202574, 25.600659772753716),
          new Point(42.42548395494743, 25.603191778063774),
          new Point(42.42383666737178, 25.605165883898735),
          new Point(42.422125976769166, 25.6060241907835),
          new Point(42.419338084663956, 25.607912465929985),
          new Point(42.41959153452304, 25.608899518847466),
          new Point(42.42060532371485, 25.611474439501762),
          new Point(42.42171413687521, 25.613963529467583),
          new Point(42.42193589715427, 25.614435598254204),
          new Point(42.42304468678425, 25.6135343760252),
          new Point(42.4234881971458, 25.614435598254204),
          new Point(42.425769058018474, 25.613577291369438),
          new Point(42.426307582503945, 25.613620206713676),
          new Point(42.426529326535686, 25.615422651171684),
          new Point(42.42760635781859, 25.61503641307354),
          new Point(42.42912684106841, 25.616838857531548),
          new Point(42.429633660622564, 25.617825910449028),
          new Point(42.43086901611779, 25.61748258769512),
          new Point(42.4316292227824, 25.622503682971),
          new Point(42.432452769597504, 25.6265377253294),
          new Point(42.434258199749216, 25.625636503100395),
          new Point(42.436190269142024, 25.627868101000786),
          new Point(42.439230782305785, 25.62851183116436),
          new Point(42.44195445007029, 25.629885122179985),
          new Point(42.44318956271136, 25.62954179942608),
          new Point(42.44451965678728, 25.63048593699932),
          new Point(42.44581805471865, 25.63173048198223),
          new Point(42.44584972262472, 25.633704587817192),
          new Point(42.44584972262472, 25.63451997935772),
          new Point(42.44451965678728, 25.63503496348858),
          new Point(42.4430945549111, 25.634906217455864),
          new Point(42.4423344873224, 25.634133741259575),
          new Point(42.441606080564206, 25.633447095751762),
          new Point(42.441036017194314, 25.63396207988262),
          new Point(42.44116269839141, 25.633618757128716),
          new Point(42.44116269839138, 25.635378286242485),
          new Point(42.43878738332459, 25.635893270373344),
          new Point(42.43466995723716, 25.637051984667778),
          new Point(42.432991236664286, 25.637609884142876),
          new Point(42.432231046519476, 25.63872568309307),
          new Point(42.43185094798943, 25.639798566699028),
          new Point(42.432231046519476, 25.641686841845512),
          new Point(42.43248444425889, 25.643660947680473),
          new Point(42.433117934125335, 25.643403455615044),
          new Point(42.43565182956043, 25.642588064074516),
          new Point(42.436982083652055, 25.641815587878227),
          new Point(42.437235462182215, 25.643446370959282),
          new Point(42.43704542838064, 25.645849630236626),
          new Point(42.434733304302895, 25.647137090563774),
          new Point(42.433466350822584, 25.6479524821043),
          new Point(42.43261114274444, 25.645677968859673),
          new Point(42.43219937173002, 25.643103048205376),
          new Point(42.43207267241209, 25.64323179423809),
          new Point(42.431914297904505, 25.64323179423809),
          new Point(42.430457233657954, 25.644648000597954),
          new Point(42.429380251357706, 25.64696542918682),
          new Point(42.427923128203695, 25.6479524821043),
          new Point(42.42741629481917, 25.64602129161358),
          new Point(42.426054159792926, 25.646579191088676),
          new Point(42.42494542338678, 25.646793767809868),
          new Point(42.42298132790505, 25.644562169909477),
          new Point(42.421555736195685, 25.6410001963377),
          new Point(42.42073204621132, 25.637910291552544),
          new Point(42.42063700436299, 25.636150762438774),
          new Point(42.42564234570253, 25.634605810046196),
          new Point(42.42621254910739, 25.63778154551983),
          new Point(42.429982096688924, 25.636579915881157),
          new Point(42.42862001741636, 25.62924139201641),
          new Point(42.42782809725627, 25.62529318034649),
          new Point(42.42776474321123, 25.62529318034649),
          new Point(42.424311850922116, 25.626366063952446),
          new Point(42.423361480219775, 25.62155954539776),
          new Point(42.42203093703374, 25.62203161418438),
          new Point(42.42177749703499, 25.620357915759087),
          new Point(42.42060532371485, 25.62078706920147),
          new Point(42.420351877953564, 25.61898462474346),
          new Point(42.4200667502476, 25.618855878710747),
          new Point(42.41860941061267, 25.62031500041485),
          new Point(42.417975774119874, 25.62031500041485),
          new Point(42.417627271319674, 25.618941709399223),
          new Point(42.41563126342479, 25.616195127367973),
          new Point(42.41436392385094, 25.613448545336723),
          new Point(42.41319161194361, 25.610873624682426),
          new Point(42.412748028750826, 25.610186979174614),
          new Point(42.41214601797107, 25.608427450060844),
          new Point(42.412462866470165, 25.606710836291313),
          new Point(42.412811397970415, 25.605637952685356),
          new Point(42.413286665076754, 25.604994222521782),
          new Point(42.413286665076754, 25.603921338915825),
          new Point(42.416518385888075, 25.602848455309868),
          new Point(42.41578967907076, 25.60177557170391),
          new Point(42.41373024446, 25.59932939708233),
          new Point(42.412938136217605, 25.597999021410942)
  );


  //  @Test
  public void writetoFile() throws Exception {
    Scanner scanner = new Scanner(this.getClass().getResourceAsStream("MTEL SZ Address.txt"));

    FileWriter fstream = new FileWriter("addresses.txt");
    BufferedWriter out = new BufferedWriter(fstream);

    int i = 1;
    while (scanner.hasNext()) {
      String address = scanner.nextLine();
      out.write(i + ". " + address + "\n");
      System.out.println(address);
      i++;
    }

    out.close();
  }

}


