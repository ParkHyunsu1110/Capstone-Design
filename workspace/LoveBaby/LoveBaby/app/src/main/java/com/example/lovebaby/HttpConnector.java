package com.example.lovebaby;

/*public class HttpConnector extends Thread{

    private ArrayList<VaccineModel> arrayList = new ArrayList<>();

    @Override
    public void run() {
        try {
            URL url = new URL("https://openapi.gg.go.kr/TbChildnatnPrvntncltnmdnstM?key=adfd8b2e53804414b96c0a2d8bb536fd&Type=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        arrayList = JsonParser.jsonParser(line);
                    }
                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {

        }
    }
    public ArrayList<VaccineModel> getList(){
        return arrayList;
    }
}*/
