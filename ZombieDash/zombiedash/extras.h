void position_sword(Sprite *player, Sprite *sword);
bool check_sword(Sprite *sword, Sprite *player, bool has_sword);
bool check_grenade(Sprite *grenade, Sprite *player, bool has_grenade);
void check_z_death(Sprite *sword, Sprite *zombie[8]);
void reset_sword(Sprite *sword,Sprite *player);
void sword_indicator(void);
void grenade_indicator(void);
void place_grenade(Sprite *player, Sprite *grenade);