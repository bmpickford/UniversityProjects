void zombie_death(Sprite *zombie);
int getZombie(void);
void resetZombies(void);
void z_turn(Sprite *zombie, int dir);
void z_play_turn(Sprite *zombie[5]);
void setup_z(Sprite zombie[5], byte *bitmap, Sprite *player, Sprite *sword, byte *s_bitmap);
void draw_z(Sprite zombie[5], Sprite *sword, Sprite *grenade);