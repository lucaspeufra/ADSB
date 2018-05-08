import java.util.ArrayList;

/**
 * Interface qui permet de déléguer la fonction de convertion du flux d'entrée
 * en liste d'objets de données.
 * @author lucas
 *
 */
public interface IParsingStrategy {
	
	public ArrayList<DataStat> parse_input (String input);

}
