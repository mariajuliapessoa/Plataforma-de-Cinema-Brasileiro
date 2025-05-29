import { Auth } from "@/lib/auth";
import { UserType } from "@/schemas/user.schema";
import { useEffect, useState } from "react";

export const useAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<UserType | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const getUser = async () => {
      setIsAuthenticated(true);

      const token = Auth.getTokenFromCookies(document.cookie);

      if (token) {
        const user = await Auth.getSession(token);
        setUser(user);
      }

      setLoading(false);
    };

    getUser();
  }, []);

  return { isAuthenticated, user, loading };
};
