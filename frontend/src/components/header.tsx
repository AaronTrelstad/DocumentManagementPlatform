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

export function Header() {
  const [drawerOpen, setDrawerOpen] = useState(false);

  const toggleDrawer = (open: boolean) => {
    setDrawerOpen(open);
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

        <Button color="inherit" to="/" component={Link} sx={{ flexGrow: 1 }}>
          Document Manager
        </Button>
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
