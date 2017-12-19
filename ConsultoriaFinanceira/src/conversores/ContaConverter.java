package conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.dao.ContaDAO;
import modelo.dominio.Conta;


@FacesConverter(value="cont-converter")
public class ContaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
			
		Integer codigo;
		try {
			codigo = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			codigo = null;
		}
		
		if (codigo != null)
		{
			ContaDAO dao = new ContaDAO();
			
			Conta cont = dao.lerPorId(codigo);
			
			return cont;  
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (value instanceof Conta)
		{
			Conta cont = (Conta) value;
			
			return cont.getCodigo().toString();
		}
		
		return null;
	}

}
