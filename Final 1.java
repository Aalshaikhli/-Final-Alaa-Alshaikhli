// 0.Documentation Section
// C7_SOS, main.c
// Runs on LM4F120 or TM4C123 LaunchPad
// Input from PF4(SW1),PF0(SW2), output to PF3 (Green LED)
// Pressing SW1 starts SOS (Green LED flashes SOS).
//    S: Toggle light 3 times with 1/2 sec gap between ON..1/2sec..OFF
//    O: Toggle light 3 times with 2 sec gap between ON..2sec..OFF
//    S: Toggle light 3 times with 1/2 sec gap between ON..1/2sec..OFF
//    5 second delay between SOS
// Pressing SW2 stops SOS
// Authors: Daniel Valvano, Jonathan Valvano and Ramesh Yerraballi
// Date: July 15, 2013
// 1. Pre-processor Directives Section
// Constant declarations to access port registers using
// symbolic names instead of addresses

#define GPIO_PORTF_PCTL_R    (*((volatile unsigned long *)0x4002552C))
#define SYSCTL_RCGC2_R       (*((volatile unsigned long *)0x400FE108))
 
// 2. Declarations Section
//   Global Variables
unsigned long SW1; // input from PF4
unsigned long SW2; // input from PF0
//   Function Prototypes
void PortF_Init(void);
void FlashSOS(void);
void delay(unsigned long halfsecs);
 
// 3. Subroutines Section
// MAIN: Mandatory for a C Program to be executable
int main(void){
  PortF_Init(); // Init port PF4 PF2 PF0   
  while(1){
    do{
      SW1 = GPIO_PORTF_DATA_R&0x10; // PF4 into SW1
    }while(SW1 == 0x10);
    do{
      FlashSOS();
              SW2 = GPIO_PORTF_DATA_R&0x01; // PF0 into SW2
            }while(SW2 == 0x01);
  }
}
 
// Subroutine to initialize port F pins for input and output
// PF4 is input SW1 and PF2 is output Blue LED
// Inputs: None
// Outputs: None
// Notes: ...
void PortF_Init(void){ volatile unsigned long delay;
  SYSCTL_RCGC2_R |= 0x00000020;   // 1) F clock
  delay = SYSCTL_RCGC2_R;         // delay  
  GPIO_PORTF_LOCK_R = 0x4C4F434B; // 2) unlock PortF PF0 
  GPIO_PORTF_CR_R |= 0x1F;        // allow changes to PF4-0      
  GPIO_PORTF_AMSEL_R &= 0x00;     // 3) disable analog function
  GPIO_PORTF_PCTL_R &= 0x00000000; // 4) GPIO clear bit PCTL 
  GPIO_PORTF_DIR_R &= ~0x11;      // 5.1) PF4,PF0 input,
  GPIO_PORTF_DIR_R |= 0x08;       // 5.2) PF3 output 
  GPIO_PORTF_AFSEL_R &= 0x00;     // 6) no alternate function
  GPIO_PORTF_PUR_R |= 0x11;       // enable pullup resistors on PF4,PF0      
  GPIO_PORTF_DEN_R |= 0x1F;       // 7) enable digital pins PF4-PF0       
}
// Color    LED(s) PortF
// dark     ---    0
// red      R--    0x02
// blue     --B    0x04
// green    -G-    0x08
// yellow   RG-    0x0A
// sky blue -GB    0x0C
// white    RGB    0x0E
 
// Subroutine to Flash a green LED SOS once
// PF3 is green LED: SOS
//    S: Toggle light 3 times with 1/2 sec gap between ON..1/2sec..OFF
//    O: Toggle light 3 times with 2 sec gap between ON..2sec..OFF
//    S: Toggle light 3 times with 1/2 sec gap between ON..1/2sec..OFF
// Inputs: None
// Outputs: None
// Notes: ...
void FlashSOS(void){
  //S
  GPIO_PORTF_DATA_R |= 0x08;  delay(1);
  GPIO_PORTF_DATA_R &= ~0x08; delay(1);
  GPIO_PORTF_DATA_R |= 0x08;  delay(1);
  GPIO_PORTF_DATA_R &= ~0x08; delay(1);
  GPIO_PORTF_DATA_R |= 0x08;  delay(1);
  GPIO_PORTF_DATA_R &= ~0x08; delay(1);
  //O
  GPIO_PORTF_DATA_R |= 0x08; delay(4);
  GPIO_PORTF_DATA_R &= ~0x08;delay(4);
  GPIO_PORTF_DATA_R |= 0x08; delay(4);
  GPIO_PORTF_DATA_R &= ~0x08;delay(4);
  GPIO_PORTF_DATA_R |= 0x08; delay(4);
  GPIO_PORTF_DATA_R &= ~0x08;delay(4);
  //S
  GPIO_PORTF_DATA_R |= 0x08; delay(1);
  GPIO_PORTF_DATA_R &= ~0x08;delay(1);
  GPIO_PORTF_DATA_R |= 0x08; delay(1);
  GPIO_PORTF_DATA_R &= ~0x08;delay(1);
  GPIO_PORTF_DATA_R |= 0x08; delay(1);
  GPIO_PORTF_DATA_R &= ~0x08;delay(1);
  delay(10); // Delay for 5 secs in between flashes
}
 
// Subroutine to delay in units of half seconds
// We will make a precise estimate later:
//   For now we assume it takes 1/2 sec to count down
//   from 2,000,000 down to zero
// Inputs: Number of half seconds to delay
// Outputs: None
// Notes: ...
void delay(unsigned long halfsecs){
  unsigned long count;
 
  while(halfsecs > 0 ) { // repeat while still halfsecs to delay
    count = 1538460;
// originally count was 400000, which took 0.13 sec to complete
// later we change it to 400000*0.5/0.13=1538460 that it takes 0.5 sec
    while (count > 0) {
      count--;
    } // This while loop takes approximately 3 cycles
    halfsecs--;
  }
}