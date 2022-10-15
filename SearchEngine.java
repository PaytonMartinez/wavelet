import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;


class Handler implements URLHandler {
    // ArrayList to hold possible search results
    ArrayList<String> searchList = new ArrayList<>();

    public String handleRequest(URI url) {

        if (url.getPath().equals("/")) {
            return String.format("Welcome to Bootleg Google!");
        }
        //Search for substring within ArrayList
        else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {

                //Value to store results
                String searchResult = "";
                //Check for substring matches
                for (int i = 0; i < searchList.size(); i++) {
                    if (searchList.get(i).contains(parameters[1])) {
                        //add if there's a match
                        searchResult += searchList.get(i) + " ";
                    }
                }
                //If results were found
                if(searchResult != "") {
                    return "Results: " + searchResult;
                }
                //If no matches were found
                else {
                    return "No Results Found.";
                }
            }
        }
        
        else {
            //Add to search list
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    searchList.add(parameters[1]);
                    return String.format("%s has been added to the search list.", parameters[1]);
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}