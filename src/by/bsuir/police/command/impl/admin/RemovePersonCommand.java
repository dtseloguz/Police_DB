package by.bsuir.police.command.impl.admin;

import by.bsuir.police.command.ActionCommand;
import by.bsuir.police.command.PagePath;
import by.bsuir.police.command.Router;
import by.bsuir.police.entity.Person;
import by.bsuir.police.exception.ServiceException;
import by.bsuir.police.service.impl.PersonServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The type Remove person command.
 */
public class RemovePersonCommand implements ActionCommand {
    private static final String ID = "id";
    /**
     * The constant MISSING.
     */
    public static final String MISSING = "missing";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        PersonServiceImpl service = new PersonServiceImpl();

        String id = request.getParameter(ID);
        Optional<Person> person = null;
        try {
            person = service.searchById(Integer.parseInt(id));
            service.remove(Integer.parseInt(id));
            ArrayList<Person> people = service.showAll();
            request.getSession().setAttribute("people", people);
            if (person.get().getStatus().equals(MISSING)) {
                router.setPagePath(PagePath.MISSING_TABLE.getJspPath());
            } else {
                router.setPagePath(PagePath.WANTED_TABLE.getJspPath());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        router.setRouteType(Router.RouteType.REDIRECT);
        return router;
    }
}
