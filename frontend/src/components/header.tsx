import {
  AppBar,
  Box,
  Button,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemText,
  Toolbar,
  Typography,
} from "@mui/material";
import { useState } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { logout } from "../slices/auth";

export function Header() {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const isAuth = useSelector((state: RootState) => state.auth.isAuthenticated);
  const dispatch = useDispatch();

  const toggleDrawer = (open: boolean) => {
    setDrawerOpen(open);
  };

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <AppBar position="static">
      <Toolbar>
        <IconButton
          size="large"
          edge="start"
          color="inherit"
          aria-label="menu"
          sx={{ mr: 2 }}
          onClick={() => toggleDrawer(true)}
        >
          <MenuIcon />
        </IconButton>

        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          <Button
            color="inherit"
            to="/"
            component={Link}
            sx={{ textTransform: "none", fontSize: "1.25rem" }}
          >
            Document Manager
          </Button>
        </Typography>

        {isAuth ? (
          <Button color="inherit" to="/login" component={Link}>
            Login
          </Button>
        ) : (
          <Button color="inherit" onClick={handleLogout}>
            Logout
          </Button>
        )}
      </Toolbar>

      <Drawer
        anchor="left"
        open={drawerOpen}
        onClose={() => toggleDrawer(false)}
      >
        <Box
          sx={{ width: 250 }}
          role="presentation"
          onClick={() => toggleDrawer(false)}
          onKeyDown={() => toggleDrawer(false)}
        >
          <List>
            <ListItem component={Link} to="/">
              <ListItemText primary="Home" />
            </ListItem>
            <ListItem component={Link} to="/dashboard">
              <ListItemText primary="Dashboard" />
            </ListItem>
          </List>
        </Box>
      </Drawer>
    </AppBar>
  );
}
